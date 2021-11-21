package com.demoversion.object.domain.theater;

public class Bag {

  private final Invitation invitation;
  private Long amount;
  private Ticket ticket;

  public Bag(final Long amount) {
    this(null, amount);
  }

  public Bag(final Invitation invitation, final Long amount) {
    this.invitation = invitation;
    this.amount = amount;
  }

  private boolean hasInvitation() {
    return invitation != null;
  }

  public boolean hasTicket() {
    return ticket != null;
  }

  private void setTicket(final Ticket ticket) {
    this.ticket = ticket;
  }

  private void minusAmount(final Long amount) {
    this.amount -= amount;
  }

  private void plusAmount(final Long amount) {
    this.amount += amount;
  }

  public Long getAmount() {
    return amount;
  }

  public Long hold(final Ticket ticket) {
    if (hasInvitation()) {
      setTicket(ticket);
      return 0L;
    } else {
      setTicket(ticket);
      minusAmount(ticket.getFea());
      return ticket.getFea();
    }
  }
}
