package com.demoversion.object.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketOffice {

  private Long amount;
  private final List<Ticket> tickets = new ArrayList<>();

  public TicketOffice(final Long amount, final Ticket... tickets) {
    this.amount = amount;
    this.tickets.addAll(Arrays.asList(tickets));
  }

  public Ticket getTicket() {
    return tickets.remove(0);
  }

  public void minusAmount(final Long amount) {
    this.amount -= amount;
  }

  public void plusAmount(final Long amount) {
    this.amount += amount;
  }
}
