package com.demoversion.object.domain;

public class Theater {

  private final TicketSeller ticketSeller;

  public Theater(final TicketSeller ticketSeller) {
    this.ticketSeller = ticketSeller;
  }

  public void enter(final Audience audience) {
    ticketSeller.sellTo(audience);
  }
}
