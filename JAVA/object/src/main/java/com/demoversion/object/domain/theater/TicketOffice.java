package com.demoversion.object.domain.theater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class TicketOffice {

  private final List<Ticket> tickets = new ArrayList<>();
  private Long amount;

  public TicketOffice(final Long amount, final Ticket... tickets) {
    this.amount = amount;
    this.tickets.addAll(Arrays.asList(tickets));
  }

  private Ticket getTicket() {
    return tickets.remove(0);
  }

  private void minusAmount(final Long amount) {
    this.amount -= amount;
  }

  private void plusAmount(final Long amount) {
    this.amount += amount;
  }

  public void sellTicketTo(final Audience audience) {
    final Ticket ticket = getTicket();
    final Long ticketFee = audience.buy(ticket);
    plusAmount(ticketFee);
  }
}
