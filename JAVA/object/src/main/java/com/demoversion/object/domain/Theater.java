package com.demoversion.object.domain;

public class Theater {

  private final TicketSeller ticketSeller;

  public Theater(final TicketSeller ticketSeller) {
    this.ticketSeller = ticketSeller;
  }

  public void enter(final Audience audience) {
    if (audience.getBag().hasInvitation()) {
      final Ticket ticket = ticketSeller.getTicketOffice().getTicket();
      audience.getBag().setTicket(ticket);
    } else {
      final Ticket ticket = ticketSeller.getTicketOffice().getTicket();
      audience.getBag().minusAmount(ticket.getFea());
      ticketSeller.getTicketOffice().plusAmount(ticket.getFea());
      audience.getBag().setTicket(ticket);
    }
  }
}
