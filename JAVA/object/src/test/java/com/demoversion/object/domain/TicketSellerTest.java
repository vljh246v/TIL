package com.demoversion.object.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TicketSellerTest {

  @Test
  void getTicketOffice() {

    final Long baseAmount = 10L;
    final Ticket baseTicket = new Ticket();

    final TicketOffice ticketOffice = new TicketOffice(baseAmount, baseTicket);

    final Ticket ticket = ticketOffice.getTicket();

    assertThat(ticket).isEqualTo(baseTicket);
    assertThat(ticketOffice.getTickets()).isEmpty();
  }
}