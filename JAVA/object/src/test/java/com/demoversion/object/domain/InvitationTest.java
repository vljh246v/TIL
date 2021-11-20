package com.demoversion.object.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class InvitationTest {

  @Test
  void getWhen() {
    final LocalDateTime now = LocalDateTime.now();

    final Invitation invitation = new Invitation();
    invitation.setWhen(now);

    assertThat(invitation.getWhen()).isEqualTo(now);
  }
}