package com.demoversion.object.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AudienceTest {

  @Test
  void getBag() {
    final Bag expected = new Bag(new Invitation(), 10L);
    final Audience audience = new Audience(expected);

    final Bag actual = audience.getBag();
    assertThat(actual).isEqualTo(expected);
  }
}