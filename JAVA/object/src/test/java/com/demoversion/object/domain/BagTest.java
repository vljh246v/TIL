package com.demoversion.object.domain;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BagTest {

  @Test
  void hold_has_invitation() {
    final Long baseAmount = 10L;
    final Invitation invitation = new Invitation();

    final Bag bag = new Bag(invitation, baseAmount);

    final Long ticketFee = 1L;
    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);

    final Long ticketAmount = bag.hold(ticket);
    assertThat(ticketAmount).isEqualTo(0L);
    assertThat(bag.hasTicket()).isTrue();
  }

  @Test
  void hold_did_not_has_invitation() {
    final Long baseAmount = 10L;
    final Bag bag = new Bag(null, baseAmount);

    final Long ticketFee = 1L;
    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);

    final Long ticketAmount = bag.hold(ticket);
    assertThat(ticketAmount).isEqualTo(ticketFee);
    assertThat(bag.hasTicket()).isTrue();
    assertThat(bag.getAmount()).isEqualTo(baseAmount - ticket.getFea());
  }
}