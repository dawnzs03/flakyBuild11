/*
 * Copyright (c) 1997, 2016, Oracle and/or its affiliates. All rights reserved.
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
 */


/*
 * The Original Code is HAT. The Initial Developer of the
 * Original Code is Bill Foote, with contributions from others
 * at JavaSoft/Sun.
 */

package jdk.test.lib.hprof.parser;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream that keeps track of total bytes read (in effect
 * 'position' in stream) from the input stream.
 *
 */
public class PositionInputStream extends FilterInputStream {
    private long position = 0L;

    public PositionInputStream(InputStream in) {
        super(in);
    }

    public int read() throws IOException {
        int res = super.read();
        if (res != -1) position++;
        return res;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int res = super.read(b, off, len);
        if (res != -1) position += res;
        return res;
    }

    public long skip(long n) throws IOException {
        long res = super.skip(n);
        position += res;
        return res;
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int readLimit) {
        throw new UnsupportedOperationException("mark");
    }

    public void reset() {
        throw new UnsupportedOperationException("reset");
    }

    public long position() {
        return position;
    }
}
