package com.demoversion.object.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TicketOfficeTest {

  @Test
  void getTicket() {

    final Long baseAmount = 10L;
    final Ticket ticket = new Ticket();
    final TicketOffice ticketOffice = new TicketOffice(baseAmount, ticket);

    assertThat(ticketOffice.getTicket()).isEqualTo(ticket);
  }

  @Test
  void minusAmount() {
    final Long baseAmount = 10L;
    final Long ticketFee = 1L;
    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);
    final TicketOffice ticketOffice = new TicketOffice(baseAmount, ticket);

    ticketOffice.minusAmount(ticket.getFea());

    assertThat(ticketOffice.getAmount()).isEqualTo(baseAmount - ticket.getFea());
  }

  @Test
  void plusAmount() {
    final Long baseAmount = 10L;
    final Long ticketFee = 1L;
    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);
    final TicketOffice ticketOffice = new TicketOffice(baseAmount, ticket);

    ticketOffice.plusAmount(ticket.getFea());

    assertThat(ticketOffice.getAmount()).isEqualTo(baseAmount + ticket.getFea());
  }
}