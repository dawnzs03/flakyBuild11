/*
 * Copyright (c) 2022, 2023, Intel Corporation. All rights reserved.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

#include "precompiled.hpp"
#include "macroAssembler_x86.hpp"
#include "stubGenerator_x86_64.hpp"

#define __ _masm->

// References:
//  - (Normative) RFC7539 - ChaCha20 and Poly1305 for IETF Protocols
//  - M. Goll and S. Gueron, "Vectorization of Poly1305 Message Authentication Code"
//  - "The design of Poly1305" https://loup-vaillant.fr/tutorials/poly1305-design

// Explanation for the 'well known' modular arithmetic optimization, reduction by pseudo-Mersene prime 2^130-5:
//
// Reduction by 2^130-5 can be expressed as follows:
//    ( ax2^130 + b ) mod 2^130-5     //i.e. number split along the 130-bit boundary
//                                 = ( ax2^130 - 5xa + 5xa + b ) mod 2^130-5
//                                 = ( ax(2^130 - 5) + 5xa + b ) mod 2^130-5 // i.e. adding multiples of modulus is a noop
//                                 = ( 5xa + b ) mod 2^130-5
// QED: shows mathematically the well known algorithm of 'split the number down the middle, multiply upper and add'
// This is particularly useful to understand when combining with 'odd-sized' limbs that might cause misallignment
//

// Pseudocode for this file (in general):
//    * used for poly1305_multiply_scalar
//    x used for poly1305_multiply8_avx512
//    lower-case variables are scalar numbers in 3x44-bit limbs (in gprs)
//    upper-case variables are 8-element vector numbers in 3x44-bit limbs (in zmm registers)
//    [ ] used to denote vector numbers (with their elements)

// Constant Pool:
ATTRIBUTE_ALIGNED(64) static const uint64_t POLY1305_PAD_MSG[] = {
  0x0000010000000000, 0x0000010000000000,
  0x0000010000000000, 0x0000010000000000,
  0x0000010000000000, 0x0000010000000000,
  0x0000010000000000, 0x0000010000000000,
};
static address poly1305_pad_msg() {
  return (address)POLY1305_PAD_MSG;
}

ATTRIBUTE_ALIGNED(64) static const uint64_t POLY1305_MASK42[] = {
  0x000003ffffffffff, 0x000003ffffffffff,
  0x000003ffffffffff, 0x000003ffffffffff,
  0x000003ffffffffff, 0x000003ffffffffff,
  0x000003ffffffffff, 0x000003ffffffffff
};
static address poly1305_mask42() {
  return (address)POLY1305_MASK42;
}

ATTRIBUTE_ALIGNED(64) static const uint64_t POLY1305_MASK44[] = {
  0x00000fffffffffff, 0x00000fffffffffff,
  0x00000fffffffffff, 0x00000fffffffffff,
  0x00000fffffffffff, 0x00000fffffffffff,
  0x00000fffffffffff, 0x00000fffffffffff,
};
static address poly1305_mask44() {
  return (address)POLY1305_MASK44;
}

// Compute product for 8 16-byte message blocks,
// i.e. For each block, compute [a2 a1 a0] = [a2 a1 a0] x [r2 r1 r0]
//
// Each block/number is represented by 3 44-bit limb digits, start with multiplication
//
//      a2       a1       a0
// x    r2       r1       r0
// ----------------------------------
//     a2xr0    a1xr0    a0xr0
// +   a1xr1    a0xr1  5xa2xr1'     (r1' = r1<<2)
// +   a0xr2  5xa2xr2' 5xa1xr2'     (r2' = r2<<2)
// ----------------------------------
//        p2       p1       p0
//
// Then, propagate the carry (bits after bit 44) from lower limbs into higher limbs.
// Then, modular reduction from upper limb wrapped to lower limbs
//
// Math Note 1: 'carry propagation' from p2 to p0 involves multiplication by 5 (i.e. slightly modified modular reduction from above):
//    ( p2x2^88 ) mod 2^130-5
//                             = ( p2'x2^88 + p2''x2^130) mod 2^130-5 // Split on 130-bit boudary
//                             = ( p2'x2^88 + p2''x2^130 - 5xp2'' + 5xp2'') mod 2^130-5
//                             = ( p2'x2^88 + p2''x(2^130 - 5) + 5xp2'') mod 2^130-5 // i.e. adding multiples of modulus is a noop
//                             = ( p2'x2^88 + 5xp2'') mod 2^130-5
//
// Math Note 2: R1P = 4*5*R1 and R2P = 4*5*R2; This precomputation allows simultaneous reduction and multiplication.
// This is not the standard 'multiply-upper-by-5', here is why the factor is 4*5 instead of 5.
// For example, partial product (a2xr2):
//    (a2x2^88)x(r2x2^88) mod 2^130-5
//                                    = (a2xr2 x 2^176) mod 2^130-5
//                                    = (a2xr2 x 2^46x2^130) mod 2^130-5
//                                    = (a2xr2x2^46 x 2^130- 5xa2xr2x2^46 + 5xa2xr2x2^46) mod 2^130-5
//                                    = (a2xr2x2^46 x (2^130- 5) + 5xa2xr2x2^46) mod 2^130-5 // i.e. adding multiples of modulus is a noop
//                                    = (5xa2xr2x2^46) mod 2^130-5
//                                    = (a2x5xr2x2^2 x 2^44) mod 2^130-5 // Align to limb boudary
//                                    = (a2x[5xr2x4] x 2^44) mod 2^130-5
//                                    = (a2xR2P x 2^44) mod 2^130-5 // i.e. R2P = 4*5*R2
//
void StubGenerator::poly1305_multiply8_avx512(
  const XMMRegister A0, const XMMRegister A1, const XMMRegister A2,
  const XMMRegister R0, const XMMRegister R1, const XMMRegister R2, const XMMRegister R1P, const XMMRegister R2P,
  const XMMRegister P0L, const XMMRegister P0H, const XMMRegister P1L, const XMMRegister P1H, const XMMRegister P2L, const XMMRegister P2H,
  const XMMRegister TMP, const Register rscratch)
{

  // Reset partial sums
  __ evpxorq(P0L, P0L, P0L, Assembler::AVX_512bit);
  __ evpxorq(P0H, P0H, P0H, Assembler::AVX_512bit);
  __ evpxorq(P1L, P1L, P1L, Assembler::AVX_512bit);
  __ evpxorq(P1H, P1H, P1H, Assembler::AVX_512bit);
  __ evpxorq(P2L, P2L, P2L, Assembler::AVX_512bit);
  __ evpxorq(P2H, P2H, P2H, Assembler::AVX_512bit);

  // Calculate partial products
  // p0 = a2xr1'
  // p1 = a2xr2'
  // p2 = a2xr0
  __ evpmadd52luq(P0L, A2, R1P, Assembler::AVX_512bit);
  __ evpmadd52huq(P0H, A2, R1P, Assembler::AVX_512bit);
  __ evpmadd52luq(P1L, A2, R2P, Assembler::AVX_512bit);
  __ evpmadd52huq(P1H, A2, R2P, Assembler::AVX_512bit);
  __ evpmadd52luq(P2L, A2, R0, Assembler::AVX_512bit);
  __ evpmadd52huq(P2H, A2, R0, Assembler::AVX_512bit);

  // p0 += a0xr0
  // p1 += a0xr1
  // p2 += a0xr2
  __ evpmadd52luq(P1L, A0, R1, Assembler::AVX_512bit);
  __ evpmadd52huq(P1H, A0, R1, Assembler::AVX_512bit);
  __ evpmadd52luq(P2L, A0, R2, Assembler::AVX_512bit);
  __ evpmadd52huq(P2H, A0, R2, Assembler::AVX_512bit);
  __ evpmadd52luq(P0L, A0, R0, Assembler::AVX_512bit);
  __ evpmadd52huq(P0H, A0, R0, Assembler::AVX_512bit);

  // p0 += a1xr2'
  // p1 += a1xr0
  // p2 += a1xr1
  __ evpmadd52luq(P0L, A1, R2P, Assembler::AVX_512bit);
  __ evpmadd52huq(P0H, A1, R2P, Assembler::AVX_512bit);
  __ evpmadd52luq(P1L, A1, R0, Assembler::AVX_512bit);
  __ evpmadd52huq(P1H, A1, R0, Assembler::AVX_512bit);
  __ evpmadd52luq(P2L, A1, R1, Assembler::AVX_512bit);
  __ evpmadd52huq(P2H, A1, R1, Assembler::AVX_512bit);

  // Carry propagation:
  // (Not quite aligned)                         | More mathematically correct:
  //         P2L   P1L   P0L                     |                 P2Lx2^88 + P1Lx2^44 + P0Lx2^0
  // + P2H   P1H   P0H                           |   + P2Hx2^140 + P1Hx2^96 + P0Hx2^52
  // ---------------------------                 |   -----------------------------------------------
  // = P2H    A2    A1    A0                     |   = P2Hx2^130 + A2x2^88 +   A1x2^44 +  A0x2^0
  //
  __ vpsrlq(TMP, P0L, 44, Assembler::AVX_512bit);
  __ evpandq(A0, P0L, ExternalAddress(poly1305_mask44()), Assembler::AVX_512bit, rscratch); // Clear top 20 bits

  __ vpsllq(P0H, P0H, 8, Assembler::AVX_512bit);
  __ vpaddq(P0H, P0H, TMP, Assembler::AVX_512bit);
  __ vpaddq(P1L, P1L, P0H, Assembler::AVX_512bit);
  __ evpandq(A1, P1L, ExternalAddress(poly1305_mask44()), Assembler::AVX_512bit, rscratch); // Clear top 20 bits

  __ vpsrlq(TMP, P1L, 44, Assembler::AVX_512bit);
  __ vpsllq(P1H, P1H, 8, Assembler::AVX_512bit);
  __ vpaddq(P1H, P1H, TMP, Assembler::AVX_512bit);
  __ vpaddq(P2L, P2L, P1H, Assembler::AVX_512bit);
  __ evpandq(A2, P2L, ExternalAddress(poly1305_mask42()), Assembler::AVX_512bit, rscratch); // Clear top 22 bits

  __ vpsrlq(TMP, P2L, 42, Assembler::AVX_512bit);
  __ vpsllq(P2H, P2H, 10, Assembler::AVX_512bit);
  __ vpaddq(P2H, P2H, TMP, Assembler::AVX_512bit);

  // Reduction: p2->a0->a1
  // Multiply by 5 the highest bits (p2 is above 130 bits)
  __ vpaddq(A0, A0, P2H, Assembler::AVX_512bit);
  __ vpsllq(P2H, P2H, 2, Assembler::AVX_512bit);
  __ vpaddq(A0, A0, P2H, Assembler::AVX_512bit);
  __ vpsrlq(TMP, A0, 44, Assembler::AVX_512bit);
  __ evpandq(A0, A0, ExternalAddress(poly1305_mask44()), Assembler::AVX_512bit, rscratch);
  __ vpaddq(A1, A1, TMP, Assembler::AVX_512bit);
}

// Compute product for a single 16-byte message blocks
// - Assumes that r = [r1 r0] is only 128 bits (not 130)
// - Input [a2 a1 a0]; when only128 is set, input is 128 bits (i.e. a2==0)
// - Output [a2 a1 a0] is at least 130 bits (i.e. a2 is used regardless of only128)
//
// Note 1: a2 here is only two bits so anything above is subject of reduction.
// Note 2: Constant c1 = 5xr1 = r1 + (r1 << 2) simplifies multiply with less operations
//
// Flow of the code below is as follows:
//
//          a2        a1        a0
//        x           r1        r0
//   -----------------------------
//       a2xr0     a1xr0     a0xr0
//   +             a0xr1
//   +           5xa2xr1   5xa1xr1
//   -----------------------------
//     [0|L2L] [L1H|L1L] [L0H|L0L]
//
//   Registers:  t2:t1     t0:a0
//
// Completing the multiply and adding (with carry) 3x128-bit limbs into
// 192-bits again (3x64-bits):
// a0 = L0L
// a1 = L0H + L1L
// t2 = L1H + L2L
void StubGenerator::poly1305_multiply_scalar(
  const Register a0, const Register a1, const Register a2,
  const Register r0, const Register r1, const Register c1, bool only128,
  const Register t0, const Register t1, const Register t2,
  const Register mulql, const Register mulqh)
{
  // mulq instruction requires/clobers rax, rdx (mulql, mulqh)

  // t2:t1 = (a0 * r1)
  __ movq(rax, r1);
  __ mulq(a0);
  __ movq(t1, rax);
  __ movq(t2, rdx);

  // t0:a0 = (a0 * r0)
  __ movq(rax, r0);
  __ mulq(a0);
  __ movq(a0, rax); // a0 not used in other operations
  __ movq(t0, rdx);

  // t2:t1 += (a1 * r0)
  __ movq(rax, r0);
  __ mulq(a1);
  __ addq(t1, rax);
  __ adcq(t2, rdx);

  // t0:a0 += (a1 * r1x5)
  __ movq(rax, c1);
  __ mulq(a1);
  __ addq(a0, rax);
  __ adcq(t0, rdx);

  // Note: a2 is clamped to 2-bits,
  //       r1/r0 is clamped to 60-bits,
  //       their product is less than 2^64.

  if (only128) { // Accumulator only 128 bits, i.e. a2 == 0
    // just move and add t0-t1 to a1
    __ movq(a1, t0);
    __ addq(a1, t1);
    __ adcq(t2, 0);
  } else {
    // t2:t1 += (a2 * r1x5)
    __ movq(a1, a2); // use a1 for a2
    __ imulq(a1, c1);
    __ addq(t1, a1);
    __ adcq(t2, 0);

    __ movq(a1, t0); // t0:a0 => a1:a0

    // t2:a1 += (a2 * r0):t1
    __ imulq(a2, r0);
    __ addq(a1, t1);
    __ adcq(t2, a2);
  }

  // At this point, 3 64-bit limbs are in t2:a1:a0
  // t2 can span over more than 2 bits so final partial reduction step is needed.
  //
  // Partial reduction (just to fit into 130 bits)
  //    a2 = t2 & 3
  //    k = (t2 & ~3) + (t2 >> 2)
  //         Y    x4  +  Y    x1
  //    a2:a1:a0 += k
  //
  // Result will be in a2:a1:a0
  __ movq(t0, t2);
  __ movl(a2, t2); // DWORD
  __ andq(t0, ~3);
  __ shrq(t2, 2);
  __ addq(t0, t2);
  __ andl(a2, 3); // DWORD

  // a2:a1:a0 += k (kept in t0)
  __ addq(a0, t0);
  __ adcq(a1, 0);
  __ adcl(a2, 0); // DWORD
}

// Convert array of 128-bit numbers in quadwords (in D0:D1) into 128-bit numbers across 44-bit limbs (in L0:L1:L2)
// Optionally pad all the numbers (i.e. add 2^128)
//
//         +-------------------------+-------------------------+
//  D0:D1  | h0 h1 g0 g1 f0 f1 e0 e1 | d0 d1 c0 c1 b0 b1 a0 a1 |
//         +-------------------------+-------------------------+
//         +-------------------------+
//  L2     | h2 d2 g2 c2 f2 b2 e2 a2 |
//         +-------------------------+
//         +-------------------------+
//  L1     | h1 d1 g1 c1 f1 b1 e1 a1 |
//         +-------------------------+
//         +-------------------------+
//  L0     | h0 d0 g0 c0 f0 b0 e0 a0 |
//         +-------------------------+
//
void StubGenerator::poly1305_limbs_avx512(
    const XMMRegister D0, const XMMRegister D1,
    const XMMRegister L0, const XMMRegister L1, const XMMRegister L2, bool padMSG,
    const XMMRegister TMP, const Register rscratch)
{
  // Interleave blocks of data
  __ evpunpckhqdq(TMP, D0, D1, Assembler::AVX_512bit);
  __ evpunpcklqdq(L0, D0, D1, Assembler::AVX_512bit);

  // Highest 42-bit limbs of new blocks
  __ vpsrlq(L2, TMP, 24, Assembler::AVX_512bit);
  if (padMSG) {
    __ evporq(L2, L2, ExternalAddress(poly1305_pad_msg()), Assembler::AVX_512bit, rscratch); // Add 2^128 to all 8 final qwords of the message
  }

  // Middle 44-bit limbs of new blocks
  __ vpsrlq(L1, L0, 44, Assembler::AVX_512bit);
  __ vpsllq(TMP, TMP, 20, Assembler::AVX_512bit);
  __ vpternlogq(L1, 0xA8, TMP, ExternalAddress(poly1305_mask44()), Assembler::AVX_512bit, rscratch); // (A OR B AND C)

  // Lowest 44-bit limbs of new blocks
  __ evpandq(L0, L0, ExternalAddress(poly1305_mask44()), Assembler::AVX_512bit, rscratch);
}

/**
 * Copy 5x26-bit (unreduced) limbs stored at Register limbs into  a2:a1:a0 (3x64-bit limbs)
 *
 * a2 is optional. When only128 is set, limbs are expected to fit into 128-bits (i.e. a1:a0 such as clamped R)
 */
void StubGenerator::poly1305_limbs(
    const Register limbs, const Register a0, const Register a1, const Register a2,
    const Register t0, const Register t1)
{
  __ movq(a0, Address(limbs, 0));
  __ movq(t0, Address(limbs, 8));
  __ shlq(t0, 26);
  __ addq(a0, t0);
  __ movq(t0, Address(limbs, 16));
  __ movq(t1, Address(limbs, 24));
  __ movq(a1, t0);
  __ shlq(t0, 52);
  __ shrq(a1, 12);
  __ shlq(t1, 14);
  __ addq(a0, t0);
  __ adcq(a1, t1);
  __ movq(t0, Address(limbs, 32));
  if (a2 != noreg) {
    __ movq(a2, t0);
    __ shrq(a2, 24);
  }
  __ shlq(t0, 40);
  __ addq(a1, t0);
  if (a2 != noreg) {
    __ adcq(a2, 0);

    // One round of reduction
    // Take bits above 130 in a2, multiply by 5 and add to a2:a1:a0
    __ movq(t0, a2);
    __ andq(t0, ~3);
    __ andq(a2, 3);
    __ movq(t1, t0);
    __ shrq(t1, 2);
    __ addq(t0, t1);

    __ addq(a0, t0);
    __ adcq(a1, 0);
    __ adcq(a2, 0);
  }
}

/**
 * Break 3x64-bit a2:a1:a0 limbs into 5x26-bit limbs and store out into 5 quadwords at address `limbs`
 */
void StubGenerator::poly1305_limbs_out(
    const Register a0, const Register a1, const Register a2,
    const Register limbs,
    const Register t0, const Register t1)
{
  // Extra round of reduction
  // Take bits above 130 in a2, multiply by 5 and add to a2:a1:a0
  __ movq(t0, a2);
  __ andq(t0, ~3);
  __ andq(a2, 3);
  __ movq(t1, t0);
  __ shrq(t1, 2);
  __ addq(t0, t1);

  __ addq(a0, t0);
  __ adcq(a1, 0);
  __ adcq(a2, 0);

  // Chop a2:a1:a0 into 26-bit limbs
  __ movl(t0, a0);
  __ andl(t0, 0x3ffffff);
  __ movq(Address(limbs, 0), t0);

  __ shrq(a0, 26);
  __ movl(t0, a0);
  __ andl(t0, 0x3ffffff);
  __ movq(Address(limbs, 8), t0);

  __ shrq(a0, 26); // 12 bits left in a0, concatenate 14 from a1
  __ movl(t0, a1);
  __ shll(t0, 12);
  __ addl(t0, a0);
  __ andl(t0, 0x3ffffff);
  __ movq(Address(limbs, 16), t0);

  __ shrq(a1, 14); // already used up 14 bits
  __ shlq(a2, 50); // a2 contains 2 bits when reduced, but $Element.limbs dont have to be fully reduced
  __ addq(a1, a2); // put remaining bits into a1

  __ movl(t0, a1);
  __ andl(t0, 0x3ffffff);
  __ movq(Address(limbs, 24), t0);

  __ shrq(a1, 26);
  __ movl(t0, a1);
  //andl(t0, 0x3ffffff); doesnt have to be fully reduced, leave remaining bit(s)
  __ movq(Address(limbs, 32), t0);
}

// This function consumes as many whole 16*16-byte blocks as available in input
// After execution, input and length will point at remaining (unprocessed) data
// and [a2 a1 a0] will contain the current accumulator value
//
// Math Note:
//    Main loop in this function multiplies each message block by r^16; And some glue before and after..
//    Proof (for brevity, split into 4 'rows' instead of 16):
//
//     hash = ((((m1*r + m2)*r + m3)*r ...  mn)*r
//          = m1*r^n + m2*r^(n-1) + ... +mn_1*r^2 + mn*r  // Horner's rule
//
//          = m1*r^n     + m4*r^(n-4) + m8*r^(n-8) ...    // split into 4 groups for brevity, same applies to 16 blocks
//          + m2*r^(n-1) + m5*r^(n-5) + m9*r^(n-9) ...
//          + m3*r^(n-2) + m6*r^(n-6) + m10*r^(n-10) ...
//          + m4*r^(n-3) + m7*r^(n-7) + m11*r^(n-11) ...
//
//          = r^4 * (m1*r^(n-4) + m4*r^(n-8) + m8 *r^(n-16) ... + mn_3)   // factor out r^4..r; same applies to 16 but r^16..r factors
//          + r^3 * (m2*r^(n-4) + m5*r^(n-8) + m9 *r^(n-16) ... + mn_2)
//          + r^2 * (m3*r^(n-4) + m6*r^(n-8) + m10*r^(n-16) ... + mn_1)
//          + r^1 * (m4*r^(n-4) + m7*r^(n-8) + m11*r^(n-16) ... + mn_0)   // Note last column: message group has no multiplier
//
//          = (((m1*r^4 + m4)*r^4 + m8 )*r^4 ... + mn_3) * r^4   // reverse Horner's rule, for each group
//          + (((m2*r^4 + m5)*r^4 + m9 )*r^4 ... + mn_2) * r^3   // each column is multiplied by r^4, except last
//          + (((m3*r^4 + m6)*r^4 + m10)*r^4 ... + mn_1) * r^2
//          + (((m4*r^4 + m7)*r^4 + m11)*r^4 ... + mn_0) * r^1
//
// Also see M. Goll and S. Gueron, "Vectorization of Poly1305 Message Authentication Code"
//
// Pseudocode:
//  * used for poly1305_multiply_scalar
//  x used for poly1305_multiply8_avx512
//  lower-case variables are scalar numbers in 3x44-bit limbs (in gprs)
//  upper-case variables are 8&16-element vector numbers in 3x44-bit limbs (in zmm registers)
//
//    CL = a       // [0 0 0 0 0 0 0 a]
//    AL = poly1305_limbs_avx512(input)
//    AH = poly1305_limbs_avx512(input+8)
//    AL = AL + C
//    input+=16, length-=16
//
//    a = r
//    a = a*r
//  r^2 = a
//    a = a*r
//  r^3 = a
//    r = a*r
//  r^4 = a
//
//    T  = r^4 || r^3 || r^2 || r
//    B  = limbs(T)           // [r^4  0  r^3  0  r^2  0  r^1  0 ]
//    CL = B >> 1             // [ 0  r^4  0  r^3  0  r^2  0  r^1]
//    R  = r^4 || r^4 || ..   // [r^4 r^4 r^4 r^4 r^4 r^4 r^4 r^4]
//    B  = BxR                // [r^8  0  r^7  0  r^6  0  r^5  0 ]
//    B  = B | CL             // [r^8 r^4 r^7 r^3 r^6 r^2 r^5 r^1]
//    CL = B
//    R  = r^8 || r^8 || ..   // [r^8 r^8 r^8 r^8 r^8 r^8 r^8 r^8]
//    B  = B x R              // [r^16 r^12 r^15 r^11 r^14 r^10 r^13 r^9]
//    CH = B
//    R = r^16 || r^16 || ..  // [r^16 r^16 r^16 r^16 r^16 r^16 r^16 r^16]
//
// for (;length>=16; input+=16, length-=16)
//     BL = poly1305_limbs_avx512(input)
//     BH = poly1305_limbs_avx512(input+8)
//     AL = AL x R
//     AH = AH x R
//     AL = AL + BL
//     AH = AH + BH
//
//  AL = AL x CL
//  AH = AH x CH
//  A = AL + AH // 16->8 blocks
//  T = A >> 4  // 8 ->4 blocks
//  A = A + T
//  T = A >> 2  // 4 ->2 blocks
//  A = A + T
//  T = A >> 1  // 2 ->1 blocks
//  A = A + T
//  a = A
//
// Register Map:
// GPRs:
//   input        = rdi
//   length       = rbx
//   accumulator  = rcx
//   R   = r8
//   a0  = rsi
//   a1  = r9
//   a2  = r10
//   r0  = r11
//   r1  = r12
//   c1  = r8;
//   t0  = r13
//   t1  = r14
//   t2  = r15
//   stack(rsp, rbp)
//   mulq(rax, rdx) in poly1305_multiply_scalar
//
// ZMMs:
//   D: xmm0-1
//   TMP: xmm2
//   T: xmm3-8
//   A: xmm9-14
//   B: xmm15-20
//   C: xmm21-26
//   R: xmm27-31
void StubGenerator::poly1305_process_blocks_avx512(
    const Register input, const Register length,
    const Register a0, const Register a1, const Register a2,
    const Register r0, const Register r1, const Register c1)
{
  Label L_process256Loop, L_process256LoopDone;
  const Register t0 = r13;
  const Register t1 = r14;
  const Register t2 = r15;
  const Register mulql = rax;
  const Register mulqh = rdx;

  const XMMRegister D0 = xmm0;
  const XMMRegister D1 = xmm1;
  const XMMRegister TMP = xmm2;

  const XMMRegister T0 = xmm3;
  const XMMRegister T1 = xmm4;
  const XMMRegister T2 = xmm5;
  const XMMRegister T3 = xmm6;
  const XMMRegister T4 = xmm7;
  const XMMRegister T5 = xmm8;

  const XMMRegister A0 = xmm9;
  const XMMRegister A1 = xmm10;
  const XMMRegister A2 = xmm11;
  const XMMRegister A3 = xmm12;
  const XMMRegister A4 = xmm13;
  const XMMRegister A5 = xmm14;

  const XMMRegister B0 = xmm15;
  const XMMRegister B1 = xmm16;
  const XMMRegister B2 = xmm17;
  const XMMRegister B3 = xmm18;
  const XMMRegister B4 = xmm19;
  const XMMRegister B5 = xmm20;

  const XMMRegister C0 = xmm21;
  const XMMRegister C1 = xmm22;
  const XMMRegister C2 = xmm23;
  const XMMRegister C3 = xmm24;
  const XMMRegister C4 = xmm25;
  const XMMRegister C5 = xmm26;

  const XMMRegister R0 = xmm27;
  const XMMRegister R1 = xmm28;
  const XMMRegister R2 = xmm29;
  const XMMRegister R1P = xmm30;
  const XMMRegister R2P = xmm31;

  // Spread accumulator into 44-bit limbs in quadwords C0,C1,C2
  __ movq(t0, a0);
  __ andq(t0, ExternalAddress(poly1305_mask44()), t1 /*rscratch*/); // First limb (Acc[43:0])
  __ movq(C0, t0);

  __ movq(t0, a1);
  __ shrdq(a0, t0, 44);
  __ andq(a0, ExternalAddress(poly1305_mask44()), t1 /*rscratch*/); // Second limb (Acc[77:52])
  __ movq(C1, a0);

  __ shrdq(a1, a2, 24);
  __ andq(a1, ExternalAddress(poly1305_mask42()), t1 /*rscratch*/); // Third limb (Acc[129:88])
  __ movq(C2, a1);

  // To add accumulator, we must unroll first loop iteration

  // Load first block of data (128 bytes) and pad
  // A0 to have bits 0-43 of all 8 blocks in 8 qwords
  // A1 to have bits 87-44 of all 8 blocks in 8 qwords
  // A2 to have bits 127-88 of all 8 blocks in 8 qwords
  __ evmovdquq(D0, Address(input, 0), Assembler::AVX_512bit);
  __ evmovdquq(D1, Address(input, 64), Assembler::AVX_512bit);
  poly1305_limbs_avx512(D0, D1, A0, A1, A2, true, TMP, t1 /*rscratch*/);

  // Add accumulator to the fist message block
  __ vpaddq(A0, A0, C0, Assembler::AVX_512bit);
  __ vpaddq(A1, A1, C1, Assembler::AVX_512bit);
  __ vpaddq(A2, A2, C2, Assembler::AVX_512bit);

  // Load next blocks of data (128 bytes)  and pad
  // A3 to have bits 0-43 of all 8 blocks in 8 qwords
  // A4 to have bits 87-44 of all 8 blocks in 8 qwords
  // A5 to have bits 127-88 of all 8 blocks in 8 qwords
  __ evmovdquq(D0, Address(input, 64*2), Assembler::AVX_512bit);
  __ evmovdquq(D1, Address(input, 64*3), Assembler::AVX_512bit);
  poly1305_limbs_avx512(D0, D1, A3, A4, A5, true, TMP, t1 /*rscratch*/);

  __ subl(length, 16*16);
  __ lea(input, Address(input,16*16));

  // Compute the powers of R^1..R^4 and form 44-bit limbs of each
  // T0 to have bits 0-127 in 4 quadword pairs
  // T1 to have bits 128-129 in alternating 8 qwords
  __ vpxorq(T1, T1, T1, Assembler::AVX_512bit);
  __ movq(T2, r0);
  __ vpinsrq(T2, T2, r1, 1);
  __ vinserti32x4(T0, T0, T2, 3);

  // Calculate R^2
  __ movq(a0, r0);
  __ movq(a1, r1);
  // "Clever": a2 not set because poly1305_multiply_scalar has a flag to indicate 128-bit accumulator
  poly1305_multiply_scalar(a0, a1, a2,
                           r0, r1, c1, true,
                           t0, t1, t2, mulql, mulqh);

  __ movq(T2, a0);
  __ vpinsrq(T2, T2, a1, 1);
  __ vinserti32x4(T0, T0, T2, 2);
  __ movq(T2, a2);
  __ vinserti32x4(T1, T1, T2, 2);

  // Calculate R^3
  poly1305_multiply_scalar(a0, a1, a2,
                           r0, r1, c1, false,
                           t0, t1, t2, mulql, mulqh);

  __ movq(T2, a0);
  __ vpinsrq(T2, T2, a1, 1);
  __ vinserti32x4(T0, T0, T2, 1);
  __ movq(T2, a2);
  __ vinserti32x4(T1, T1, T2, 1);

  // Calculate R^4
  poly1305_multiply_scalar(a0, a1, a2,
                           r0, r1, c1, false,
                           t0, t1, t2, mulql, mulqh);

  __ movq(T2, a0);
  __ vpinsrq(T2, T2, a1, 1);
  __ vinserti32x4(T0, T0, T2, 0);
  __ movq(T2, a2);
  __ vinserti32x4(T1, T1, T2, 0);

  // Interleave the powers of R^1..R^4 to form 44-bit limbs (half-empty)
  // B0 to have bits 0-43 of all 4 blocks in alternating 8 qwords
  // B1 to have bits 87-44 of all 4 blocks in alternating 8 qwords
  // B2 to have bits 127-88 of all 4 blocks in alternating 8 qwords
  __ vpxorq(T2, T2, T2, Assembler::AVX_512bit);
  poly1305_limbs_avx512(T0, T2, B0, B1, B2, false, TMP, t1 /*rscratch*/);

  // T1 contains the 2 highest bits of the powers of R
  __ vpsllq(T1, T1, 40, Assembler::AVX_512bit);
  __ evporq(B2, B2, T1, Assembler::AVX_512bit);

  // Broadcast 44-bit limbs of R^4 into R0,R1,R2
  __ mov(t0, a0);
  __ andq(t0, ExternalAddress(poly1305_mask44()), t1 /*rscratch*/); // First limb (R^4[43:0])
  __ evpbroadcastq(R0, t0, Assembler::AVX_512bit);

  __ movq(t0, a1);
  __ shrdq(a0, t0, 44);
  __ andq(a0, ExternalAddress(poly1305_mask44()), t1 /*rscratch*/); // Second limb (R^4[87:44])
  __ evpbroadcastq(R1, a0, Assembler::AVX_512bit);

  __ shrdq(a1, a2, 24);
  __ andq(a1, ExternalAddress(poly1305_mask42()), t1 /*rscratch*/); // Third limb (R^4[129:88])
  __ evpbroadcastq(R2, a1, Assembler::AVX_512bit);

  // Generate 4*5*R^4 into {R2P,R1P}
  // Used as multiplier in poly1305_multiply8_avx512 so can
  // ignore bottom limb and carry propagation
  __ vpsllq(R1P, R1, 2, Assembler::AVX_512bit);    // 4*R^4
  __ vpsllq(R2P, R2, 2, Assembler::AVX_512bit);
  __ vpaddq(R1P, R1P, R1, Assembler::AVX_512bit);  // 5*R^4
  __ vpaddq(R2P, R2P, R2, Assembler::AVX_512bit);
  __ vpsllq(R1P, R1P, 2, Assembler::AVX_512bit);   // 4*5*R^4
  __ vpsllq(R2P, R2P, 2, Assembler::AVX_512bit);

  // Move R^4..R^1 one element over
  __ vpslldq(C0, B0, 8, Assembler::AVX_512bit);
  __ vpslldq(C1, B1, 8, Assembler::AVX_512bit);
  __ vpslldq(C2, B2, 8, Assembler::AVX_512bit);

  // Calculate R^8-R^5
  poly1305_multiply8_avx512(B0, B1, B2,             // ACC=R^4..R^1
                            R0, R1, R2, R1P, R2P,   // R^4..R^4, 4*5*R^4
                            T0, T1, T2, T3, T4, T5, TMP, t1 /*rscratch*/);

  // Interleave powers of R: R^8 R^4 R^7 R^3 R^6 R^2 R^5 R
  __ evporq(B0, B0, C0, Assembler::AVX_512bit);
  __ evporq(B1, B1, C1, Assembler::AVX_512bit);
  __ evporq(B2, B2, C2, Assembler::AVX_512bit);

  // Store R^8-R for later use
  __ evmovdquq(C0, B0, Assembler::AVX_512bit);
  __ evmovdquq(C1, B1, Assembler::AVX_512bit);
  __ evmovdquq(C2, B2, Assembler::AVX_512bit);

  // Broadcast R^8
  __ vpbroadcastq(R0, B0, Assembler::AVX_512bit);
  __ vpbroadcastq(R1, B1, Assembler::AVX_512bit);
  __ vpbroadcastq(R2, B2, Assembler::AVX_512bit);

  // Generate 4*5*R^8
  __ vpsllq(R1P, R1, 2, Assembler::AVX_512bit);
  __ vpsllq(R2P, R2, 2, Assembler::AVX_512bit);
  __ vpaddq(R1P, R1P, R1, Assembler::AVX_512bit);    // 5*R^8
  __ vpaddq(R2P, R2P, R2, Assembler::AVX_512bit);
  __ vpsllq(R1P, R1P, 2, Assembler::AVX_512bit);     // 4*5*R^8
  __ vpsllq(R2P, R2P, 2, Assembler::AVX_512bit);

  // Calculate R^16-R^9
  poly1305_multiply8_avx512(B0, B1, B2,            // ACC=R^8..R^1
                            R0, R1, R2, R1P, R2P,  // R^8..R^8, 4*5*R^8
                            T0, T1, T2, T3, T4, T5, TMP, t1 /*rscratch*/);

  // Store R^16-R^9 for later use
  __ evmovdquq(C3, B0, Assembler::AVX_512bit);
  __ evmovdquq(C4, B1, Assembler::AVX_512bit);
  __ evmovdquq(C5, B2, Assembler::AVX_512bit);

  // Broadcast R^16
  __ vpbroadcastq(R0, B0, Assembler::AVX_512bit);
  __ vpbroadcastq(R1, B1, Assembler::AVX_512bit);
  __ vpbroadcastq(R2, B2, Assembler::AVX_512bit);

  // Generate 4*5*R^16
  __ vpsllq(R1P, R1, 2, Assembler::AVX_512bit);
  __ vpsllq(R2P, R2, 2, Assembler::AVX_512bit);
  __ vpaddq(R1P, R1P, R1, Assembler::AVX_512bit);  // 5*R^16
  __ vpaddq(R2P, R2P, R2, Assembler::AVX_512bit);
  __ vpsllq(R1P, R1P, 2, Assembler::AVX_512bit);   // 4*5*R^16
  __ vpsllq(R2P, R2P, 2, Assembler::AVX_512bit);

  // VECTOR LOOP: process 16 * 16-byte message block at a time
  __ bind(L_process256Loop);
  __ cmpl(length, 16*16);
  __ jcc(Assembler::less, L_process256LoopDone);

  // Load and interleave next block of data (128 bytes)
  __ evmovdquq(D0, Address(input, 0), Assembler::AVX_512bit);
  __ evmovdquq(D1, Address(input, 64), Assembler::AVX_512bit);
  poly1305_limbs_avx512(D0, D1, B0, B1, B2, true, TMP, t1 /*rscratch*/);

  // Load and interleave next block of data (128 bytes)
  __ evmovdquq(D0, Address(input, 64*2), Assembler::AVX_512bit);
  __ evmovdquq(D1, Address(input, 64*3), Assembler::AVX_512bit);
  poly1305_limbs_avx512(D0, D1, B3, B4, B5, true, TMP, t1 /*rscratch*/);

  poly1305_multiply8_avx512(A0, A1, A2,            // MSG/ACC 16 blocks
                            R0, R1, R2, R1P, R2P,  // R^16..R^16, 4*5*R^16
                            T0, T1, T2, T3, T4, T5, TMP, t1 /*rscratch*/);
  poly1305_multiply8_avx512(A3, A4, A5,            // MSG/ACC 16 blocks
                            R0, R1, R2, R1P, R2P,  // R^16..R^16, 4*5*R^16
                            T0, T1, T2, T3, T4, T5, TMP, t1 /*rscratch*/);

  __ vpaddq(A0, A0, B0, Assembler::AVX_512bit); // Add low 42-bit bits from new blocks to accumulator
  __ vpaddq(A1, A1, B1, Assembler::AVX_512bit); // Add medium 42-bit bits from new blocks to accumulator
  __ vpaddq(A2, A2, B2, Assembler::AVX_512bit); // Add highest bits from new blocks to accumulator
  __ vpaddq(A3, A3, B3, Assembler::AVX_512bit); // Add low 42-bit bits from new blocks to accumulator
  __ vpaddq(A4, A4, B4, Assembler::AVX_512bit); // Add medium 42-bit bits from new blocks to accumulator
  __ vpaddq(A5, A5, B5, Assembler::AVX_512bit); // Add highest bits from new blocks to accumulator

  __ subl(length, 16*16);
  __ lea(input, Address(input,16*16));
  __ jmp(L_process256Loop);

  __ bind(L_process256LoopDone);

  // Tail processing: Need to multiply ACC by R^16..R^1 and add it all up into a single scalar value
  // Generate 4*5*[R^16..R^9] (ignore lowest limb)
  // Use D0 ~ R1P, D1 ~ R2P for higher powers
  __ vpsllq(R1P, C4, 2, Assembler::AVX_512bit);
  __ vpsllq(R2P, C5, 2, Assembler::AVX_512bit);
  __ vpaddq(R1P, R1P, C4, Assembler::AVX_512bit);    // 5*R^8
  __ vpaddq(R2P, R2P, C5, Assembler::AVX_512bit);
  __ vpsllq(D0, R1P, 2, Assembler::AVX_512bit);      // 4*5*R^8
  __ vpsllq(D1, R2P, 2, Assembler::AVX_512bit);

  // Generate 4*5*[R^8..R^1] (ignore lowest limb)
  __ vpsllq(R1P, C1, 2, Assembler::AVX_512bit);
  __ vpsllq(R2P, C2, 2, Assembler::AVX_512bit);
  __ vpaddq(R1P, R1P, C1, Assembler::AVX_512bit);    // 5*R^8
  __ vpaddq(R2P, R2P, C2, Assembler::AVX_512bit);
  __ vpsllq(R1P, R1P, 2, Assembler::AVX_512bit);     // 4*5*R^8
  __ vpsllq(R2P, R2P, 2, Assembler::AVX_512bit);

  poly1305_multiply8_avx512(A0, A1, A2,            // MSG/ACC 16 blocks
                            C3, C4, C5, D0, D1,    // R^16-R^9, R1P, R2P
                            T0, T1, T2, T3, T4, T5, TMP, t1 /*rscratch*/);
  poly1305_multiply8_avx512(A3, A4, A5,            // MSG/ACC 16 blocks
                            C0, C1, C2, R1P, R2P,  // R^8-R, R1P, R2P
                            T0, T1, T2, T3, T4, T5, TMP, t1 /*rscratch*/);

  // Add all blocks (horizontally)
  // 16->8 blocks
  __ vpaddq(A0, A0, A3, Assembler::AVX_512bit);
  __ vpaddq(A1, A1, A4, Assembler::AVX_512bit);
  __ vpaddq(A2, A2, A5, Assembler::AVX_512bit);

  // 8 -> 4 blocks
  __ vextracti64x4(T0, A0, 1);
  __ vextracti64x4(T1, A1, 1);
  __ vextracti64x4(T2, A2, 1);
  __ vpaddq(A0, A0, T0, Assembler::AVX_256bit);
  __ vpaddq(A1, A1, T1, Assembler::AVX_256bit);
  __ vpaddq(A2, A2, T2, Assembler::AVX_256bit);

  // 4 -> 2 blocks
  __ vextracti32x4(T0, A0, 1);
  __ vextracti32x4(T1, A1, 1);
  __ vextracti32x4(T2, A2, 1);
  __ vpaddq(A0, A0, T0, Assembler::AVX_128bit);
  __ vpaddq(A1, A1, T1, Assembler::AVX_128bit);
  __ vpaddq(A2, A2, T2, Assembler::AVX_128bit);

  // 2 -> 1 blocks
  __ vpsrldq(T0, A0, 8, Assembler::AVX_128bit);
  __ vpsrldq(T1, A1, 8, Assembler::AVX_128bit);
  __ vpsrldq(T2, A2, 8, Assembler::AVX_128bit);

  // Finish folding and clear second qword
  __ mov64(t0, 0xfd);
  __ kmovql(k1, t0);
  __ evpaddq(A0, k1, A0, T0, false, Assembler::AVX_512bit);
  __ evpaddq(A1, k1, A1, T1, false, Assembler::AVX_512bit);
  __ evpaddq(A2, k1, A2, T2, false, Assembler::AVX_512bit);

  // Carry propagation
  __ vpsrlq(D0, A0, 44, Assembler::AVX_512bit);
  __ evpandq(A0, A0, ExternalAddress(poly1305_mask44()), Assembler::AVX_512bit, t1 /*rscratch*/); // Clear top 20 bits
  __ vpaddq(A1, A1, D0, Assembler::AVX_512bit);
  __ vpsrlq(D0, A1, 44, Assembler::AVX_512bit);
  __ evpandq(A1, A1, ExternalAddress(poly1305_mask44()), Assembler::AVX_512bit, t1 /*rscratch*/); // Clear top 20 bits
  __ vpaddq(A2, A2, D0, Assembler::AVX_512bit);
  __ vpsrlq(D0, A2, 42, Assembler::AVX_512bit);
  __ evpandq(A2, A2, ExternalAddress(poly1305_mask42()), Assembler::AVX_512bit, t1 /*rscratch*/); // Clear top 22 bits
  __ vpsllq(D1, D0, 2, Assembler::AVX_512bit);
  __ vpaddq(D0, D0, D1, Assembler::AVX_512bit);
  __ vpaddq(A0, A0, D0, Assembler::AVX_512bit);

  // Put together A (accumulator)
  __ movq(a0, A0);

  __ movq(t0, A1);
  __ movq(t1, t0);
  __ shlq(t1, 44);
  __ shrq(t0, 20);

  __ movq(a2, A2);
  __ movq(a1, a2);
  __ shlq(a1, 24);
  __ shrq(a2, 40);

  __ addq(a0, t1);
  __ adcq(a1, t0);
  __ adcq(a2, 0);

  // Cleanup
  // Zero out zmm0-zmm31.
  __ vzeroall();
  for (XMMRegister rxmm = xmm16; rxmm->is_valid(); rxmm = rxmm->successor()) {
    __ vpxorq(rxmm, rxmm, rxmm, Assembler::AVX_512bit);
  }
}

// This function consumes as many whole 16-byte blocks as available in input
// After execution, input and length will point at remaining (unprocessed) data
// and accumulator will point to the current accumulator value
address StubGenerator::generate_poly1305_processBlocks() {
  __ align(CodeEntryAlignment);
  StubCodeMark mark(this, "StubRoutines", "poly1305_processBlocks");
  address start = __ pc();
  __ enter();

  // Save all 'SOE' registers
  __ push(rbx);
  #ifdef _WIN64
  __ push(rsi);
  __ push(rdi);
  #endif
  __ push(r12);
  __ push(r13);
  __ push(r14);
  __ push(r15);

  // Register Map
  const Register input        = rdi;
  const Register length       = rbx;
  const Register accumulator  = rcx;
  const Register R            = r8;

  const Register a0 = rsi;  // [in/out] accumulator bits 63..0
  const Register a1 = r9;   // [in/out] accumulator bits 127..64
  const Register a2 = r10;  // [in/out] accumulator bits 195..128
  const Register r0 = r11;  // R constant bits 63..0
  const Register r1 = r12;  // R constant bits 127..64
  const Register c1 = r8;   // 5*R (upper limb only)
  const Register t0 = r13;
  const Register t1 = r14;
  const Register t2 = r15;
  const Register mulql = rax;
  const Register mulqh = rdx;

  // Normalize input
  // pseudo-signature: void poly1305_processBlocks(byte[] input, int length, int[5] accumulator, int[5] R)
  // input, a, r pointers point at first array element
  // java headers bypassed in LibraryCallKit::inline_poly1305_processBlocks
  #ifdef _WIN64
  // c_rarg0 - rcx
  // c_rarg1 - rdx
  // c_rarg2 - r8
  // c_rarg3 - r9
  __ mov(input, c_rarg0);
  __ mov(length, c_rarg1);
  __ mov(accumulator, c_rarg2);
  __ mov(R, c_rarg3);
  #else
  // c_rarg0 - rdi
  // c_rarg1 - rsi
  // c_rarg2 - rdx
  // c_rarg3 - rcx
  // dont clobber R, args copied out-of-order
  __ mov(length, c_rarg1);
  __ mov(R, c_rarg3);
  __ mov(accumulator, c_rarg2);
  #endif

  Label L_process16Loop, L_process16LoopDone;

  // Load R into r1:r0
  poly1305_limbs(R, r0, r1, noreg, t0, t1);

  // Compute 5*R (Upper limb only)
  __ movq(c1, r1);
  __ shrq(c1, 2);
  __ addq(c1, r1); // c1 = r1 + (r1 >> 2)

  // Load accumulator into a2:a1:a0
  poly1305_limbs(accumulator, a0, a1, a2, t0, t1);

  // VECTOR LOOP: Minimum of 256 bytes to run vectorized code
  __ cmpl(length, 16*16);
  __ jcc(Assembler::less, L_process16Loop);

  poly1305_process_blocks_avx512(input, length,
                                  a0, a1, a2,
                                  r0, r1, c1);

  // SCALAR LOOP: process one 16-byte message block at a time
  __ bind(L_process16Loop);
  __ cmpl(length, 16);
  __ jcc(Assembler::less, L_process16LoopDone);

  __ addq(a0, Address(input,0));
  __ adcq(a1, Address(input,8));
  __ adcq(a2,1);
  poly1305_multiply_scalar(a0, a1, a2,
                           r0, r1, c1, false,
                           t0, t1, t2, mulql, mulqh);

  __ subl(length, 16);
  __ lea(input, Address(input,16));
  __ jmp(L_process16Loop);
  __ bind(L_process16LoopDone);

  // Write output
  poly1305_limbs_out(a0, a1, a2, accumulator, t0, t1);

  __ pop(r15);
  __ pop(r14);
  __ pop(r13);
  __ pop(r12);
  #ifdef _WIN64
  __ pop(rdi);
  __ pop(rsi);
  #endif
  __ pop(rbx);

  __ leave();
  __ ret(0);
  return start;
}
