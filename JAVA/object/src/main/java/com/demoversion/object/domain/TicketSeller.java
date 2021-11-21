package com.demoversion.object.domain;

public class TicketSeller {

  private final TicketOffice ticketOffice;

  public TicketSeller(final TicketOffice ticketOffice) {
    this.ticketOffice = ticketOffice;
  }

  public void sellTo(final Audience audience) {
    ticketOffice.sellTicketTo(audience);
  }
}
