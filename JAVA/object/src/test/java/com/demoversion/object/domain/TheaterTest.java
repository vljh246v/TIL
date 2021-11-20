package com.demoversion.object.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TheaterTest {

  @Test
  void enter_hasInvitation_audience() {

    final Long ticketOfficeBaseAmount = 10L;
    final Ticket baseTicket = new Ticket();
    final TicketOffice ticketOffice = new TicketOffice(ticketOfficeBaseAmount, baseTicket);
    final TicketSeller ticketSeller = new TicketSeller(ticketOffice);
    final Theater theater = new Theater(ticketSeller);

    final Invitation invitation = new Invitation();
    final Long audienceBaseAmount = 10L;
    final Bag bag = new Bag(invitation, audienceBaseAmount);
    final Audience audience = new Audience(bag);

    theater.enter(audience);

    assertThat(bag.hasTicket()).isTrue();
    assertThat(ticketOffice.getTickets()).isEmpty();
    assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeBaseAmount);
    assertThat(bag.getAmount()).isEqualTo(audienceBaseAmount);
  }

  @Test
  void enter_did_not_hasInvitation_audience() {

    final Long ticketOfficeBaseAmount = 10L;
    final Ticket baseTicket = new Ticket();
    baseTicket.setFea(1L);
    final TicketOffice ticketOffice = new TicketOffice(ticketOfficeBaseAmount, baseTicket);
    final TicketSeller ticketSeller = new TicketSeller(ticketOffice);
    final Theater theater = new Theater(ticketSeller);

    final Long audienceBaseAmount = 10L;
    final Bag bag = new Bag(audienceBaseAmount);
    final Audience audience = new Audience(bag);

    theater.enter(audience);

    assertThat(bag.hasTicket()).isTrue();
    assertThat(ticketOffice.getTickets()).isEmpty();
    assertThat(ticketOffice.getAmount()).isEqualTo(ticketOfficeBaseAmount + baseTicket.getFea());
    assertThat(bag.getAmount()).isEqualTo(audienceBaseAmount - baseTicket.getFea());
  }
}