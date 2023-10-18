/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Generated by jextract

package org.apache.tomcat.util.openssl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
/**
 * {@snippet :
 * void (*SSL_set_info_callback$cb)(const SSL*,int,int);
 * }
 */
public interface SSL_set_info_callback$cb {

    void apply(java.lang.foreign.MemorySegment _x0, int _x1, int _x2);
    static MemorySegment allocate(SSL_set_info_callback$cb fi, Arena scope) {
        return RuntimeHelper.upcallStub(constants$21.SSL_set_info_callback$cb_UP$MH, fi, constants$21.SSL_set_info_callback$cb$FUNC, scope);
    }
    static SSL_set_info_callback$cb ofAddress(MemorySegment addr, Arena arena) {
        MemorySegment symbol = addr.reinterpret(arena, null);
        return (java.lang.foreign.MemorySegment __x0, int __x1, int __x2) -> {
            try {
                constants$21.SSL_set_info_callback$cb_DOWN$MH.invokeExact(symbol, __x0, __x1, __x2);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}


