package com.demoversion.object.domain.theater;

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

  @Test
  void buy_hasInvitation() {
    final Long ticketFee = 1L;
    final Invitation invitation = new Invitation();
    final Bag expected = new Bag(invitation, 10L);
    final Audience audience = new Audience(expected);

    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);
    final Long buy = audience.buy(ticket);

    assertThat(buy).isEqualTo(0L);
  }

  @Test
  void buy_did_not_hasInvitation() {
    final Long ticketFee = 1L;
    final Bag expected = new Bag(null, 10L);
    final Audience audience = new Audience(expected);

    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);
    final Long buy = audience.buy(ticket);

    assertThat(buy).isEqualTo(ticketFee);
  }
}