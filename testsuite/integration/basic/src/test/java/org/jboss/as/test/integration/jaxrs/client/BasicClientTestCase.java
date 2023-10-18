/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.as.test.integration.jaxrs.client;

import java.net.URL;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.test.integration.jaxrs.packaging.war.WebXml;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Use jaxrs client to send http requests to the server.
 *
 * @author <a href="mailto:kanovotn@redhat.com">Katerina Novotna</a>
 */
@RunWith(Arquillian.class)
@RunAsClient
public class BasicClientTestCase {

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "jaxrsapp.war");
        war.addClasses(BasicClientTestCase.class, ClientResource.class);
        war.addAsWebInfResource(WebXml.get("<servlet-mapping>\n" +
                "        <servlet-name>jakarta.ws.rs.core.Application</servlet-name>\n" +
                "        <url-pattern>/myjaxrs/*</url-pattern>\n" +
                "    </servlet-mapping>\n" +
                "\n"), "web.xml");
        return war;
    }

    @ArquillianResource
    private URL url;

    static Client client;

    @BeforeClass
    public static void setUpClient() {
        client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void close() {
        client.close();
    }

    @Test
    public void testGet() throws Exception {
        String result = client.target(url.toExternalForm() + "myjaxrs/client")
                .request("text/plain").get(String.class);
        Assert.assertEquals("GET: Hello World!", result);
    }

    @Test
    public void testPost() throws Exception {
        String result = client.target(url.toExternalForm() + "myjaxrs/client")
                .request("text/plain").post(Entity.text("David"), String.class);
        Assert.assertEquals("POST: David", result);
    }

    @Test
    public void testPut() throws Exception {
        String result = client.target(url.toExternalForm() + "myjaxrs/client")
                .request("text/plain").put(Entity.text("Michael"), String.class);
        Assert.assertEquals("PUT: Michael", result);
    }

    @Test
    public void testDelete() throws Exception {
        String result = client.target(url.toExternalForm() + "myjaxrs/client")
                .request("text/plain").delete(String.class);
        Assert.assertEquals("DELETE:", result);
    }
}
