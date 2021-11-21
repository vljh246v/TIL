package com.demoversion.object.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TicketOfficeTest {

  @Test
  void sellTicketTo_has_Invitation_Audience() {

    final Long baseAmount = 10L;
    final Long ticketFee = 1L;
    final Bag bag = new Bag(new Invitation(), 10L);
    final Audience audience = new Audience(bag);
    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);
    final TicketOffice ticketOffice = new TicketOffice(baseAmount, ticket);

    ticketOffice.sellTicketTo(audience);
    assertThat(ticketOffice.getAmount()).isEqualTo(baseAmount);
  }

  @Test
  void sellTicketTo_did_not_has_Invitation_Audience() {

    final Long baseAmount = 10L;
    final Long ticketFee = 1L;
    final Bag bag = new Bag(null, 10L);
    final Audience audience = new Audience(bag);
    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);
    final TicketOffice ticketOffice = new TicketOffice(baseAmount, ticket);

    ticketOffice.sellTicketTo(audience);
    assertThat(ticketOffice.getAmount()).isEqualTo(baseAmount + ticketFee);
  }


}