package com.codeborne.selenide.collections;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class SizeLessThanTest {
  @Test
  void testToString() {
    assertThat(new SizeLessThan(10))
      .hasToString("size < 10");
  }
}
