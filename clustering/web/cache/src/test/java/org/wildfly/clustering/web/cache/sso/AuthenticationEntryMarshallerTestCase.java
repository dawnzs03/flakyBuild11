/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.clustering.web.cache.sso;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.wildfly.clustering.marshalling.protostream.ProtoStreamTesterFactory;

/**
 * Unit test for {@link AuthenticationEntryExternalizer}.
 * @author Paul Ferraro
 */
public class AuthenticationEntryMarshallerTestCase {

    @Test
    public void test() throws IOException {
        ProtoStreamTesterFactory.INSTANCE.<AuthenticationEntry<String, Object>>createTester().test(new AuthenticationEntry<>("username"), AuthenticationEntryMarshallerTestCase::assertEquals);
    }

    static void assertEquals(AuthenticationEntry<String, Object> entry1, AuthenticationEntry<String, Object> entry2) {
        Assert.assertEquals(entry1.getAuthentication(), entry2.getAuthentication());
    }
}
