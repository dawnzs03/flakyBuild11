/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.web.templ;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.file.FileSystemOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.common.template.TemplateEngine;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@RunWith(VertxUnitRunner.class)
public class ThymeleafTemplateTest {

  private static Vertx vertx;

  @BeforeClass
  public static void before() {
    vertx = Vertx.vertx(new VertxOptions().setFileSystemOptions(new FileSystemOptions().setFileCachingEnabled(true)));
  }

  @Test
  public void testTemplateHandlerOnClasspath(TestContext should) {
    TemplateEngine engine = ThymeleafTemplateEngine.create(vertx);

    final JsonObject context = new JsonObject()
      .put("foo", "badger")
      .put("bar", "fox")
      .put("context", new JsonObject().put("path", "/test-thymeleaf-template2.html"));

    engine.render(context, "somedir/test-thymeleaf-template2.html").onComplete(should.asyncAssertSuccess(render -> {
      final String expected =
        "<!doctype html>\n" +
          "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
          "<head>\n" +
          "  <meta charset=\"utf-8\">\n" +
          "</head>\n" +
          "<body>\n" +
          "<p>badger</p>\n" +
          "<p>fox</p>\n" +
          "<p>/test-thymeleaf-template2.html</p>\n" +
          "</body>\n" +
          "</html>\n";

      should.assertEquals(expected, normalizeCRLF(render.toString()));
    }));
  }

  @Test
  public void testTemplateHandlerOnFileSystem(TestContext should) {
    TemplateEngine engine = ThymeleafTemplateEngine.create(vertx);

    final JsonObject context = new JsonObject()
      .put("foo", "badger")
      .put("bar", "fox")
      .put("context", new JsonObject().put("path", "/test-thymeleaf-template2.html"));

    engine.render(context, "src/test/filesystemtemplates/test-thymeleaf-template3.html").onComplete(should.asyncAssertSuccess(render -> {
      final String expected =
        "<!doctype html>\n" +
          "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
          "<head>\n" +
          "  <meta charset=\"utf-8\">\n" +
          "</head>\n" +
          "<body>\n" +
          "<p>badger</p>\n" +
          "<p>fox</p>\n" +
          "<p>/test-thymeleaf-template2.html</p>\n" +
          "</body>\n" +
          "</html>\n";

      should.assertEquals(expected, normalizeCRLF(render.toString()));
    }));
  }

  @Test
  public void testTemplateHandlerOnClasspathDisableCaching(TestContext should) {
    System.setProperty("vertxweb.environment", "development");
    testTemplateHandlerOnClasspath(should);
  }

  @Test
  public void testNoSuchTemplate(TestContext should) {
    TemplateEngine engine = ThymeleafTemplateEngine.create(vertx);

    final JsonObject context = new JsonObject();

    engine.render(context, "nosuchtemplate.html").onComplete(should.asyncAssertFailure());
  }

  @Test
  public void testWithLocale(TestContext should) {
    TemplateEngine engine = ThymeleafTemplateEngine.create(vertx);

    final JsonObject context = new JsonObject()
      .put("foo", "badger")
      .put("bar", "fox")
      .put("lang", "en-gb")
      .put("context", new JsonObject().put("path", "/test-thymeleaf-template2.html"));

    engine.render(context, "somedir/test-thymeleaf-template2.html").onComplete(should.asyncAssertSuccess(render -> {
      final String expected =
        "<!doctype html>\n" +
          "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
          "<head>\n" +
          "  <meta charset=\"utf-8\">\n" +
          "</head>\n" +
          "<body>\n" +
          "<p>badger</p>\n" +
          "<p>fox</p>\n" +
          "<p>/test-thymeleaf-template2.html</p>\n" +
          "</body>\n" +
          "</html>\n";

      should.assertEquals(expected, normalizeCRLF(render.toString()));
    }));
  }

  @Test
  public void testGetThymeLeafTemplateEngine() {
    ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create(vertx);
    assertNotNull(engine.unwrap());
  }

  @Test
  public void testFragmentedTemplates(TestContext should) {
    TemplateEngine engine = ThymeleafTemplateEngine.create(vertx);

    final JsonObject context = new JsonObject()
      .put("foo", "badger")
      .put("bar", "fox")
      .put("context", new JsonObject().put("path", "/test-thymeleaf-template2.html"));

    engine.render(context, "somedir/test-thymeleaf-fragmented.html").onComplete(should.asyncAssertSuccess(render -> {
      final String expected =
        "<!doctype html>\n" +
          "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
          "<head>\n" +
          "  <meta charset=\"utf-8\">\n" +
          "  <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"/resources/images/favicon.png\">\n" +
          "</head>\n" +
          "<body>\n" +
          "<p>badger</p>\n" +
          "<p>fox</p>\n" +
          "<p>/test-thymeleaf-template2.html</p>\n" +
          "</body>\n" +
          "</html>\n";

      should.assertEquals(expected, normalizeCRLF(render.toString()));
    }));
  }

  @Test
  public void testCachingEnabled(TestContext should) throws IOException {
    System.setProperty("vertxweb.environment", "production");
    TemplateEngine engine = ThymeleafTemplateEngine.create(vertx);

    File temp = File.createTempFile("template", ".html", new File("target/classes"));
    temp.deleteOnExit();

    try (PrintWriter out = new PrintWriter(temp)) {
      out.print("before");
      out.flush();
    }

    engine.render(new JsonObject(), temp.getParent() + "/" + temp.getName()).onComplete(should.asyncAssertSuccess(render -> {
      should.assertEquals("before", normalizeCRLF(render.toString()));
      // cache is enabled so if we change the content that should not affect the result

      try (PrintWriter out2 = new PrintWriter(temp)) {
        out2.print("after");
        out2.flush();
      } catch (IOException e) {
        should.fail(e);
      }

      engine.render(new JsonObject(), temp.getParent() + "/" + temp.getName()).onComplete(should.asyncAssertSuccess(render2 -> {
        should.assertEquals("before", normalizeCRLF(render2.toString()));
      }));
    }));
  }

  // For windows testing
  static String normalizeCRLF(String s) {
    return s.replace("\r\n", "\n");
  }
}
