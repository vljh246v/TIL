package com.demoversion.object.domain;

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

  public boolean hasInvitation() {
    return invitation != null;
  }

  public boolean hasTicket() {
    return ticket != null;
  }

  public void setTicket(final Ticket ticket) {
    this.ticket = ticket;
  }

  public void minusAmount(final Long amount) {
    this.amount -= amount;
  }

  public void plusAmount(final Long amount) {
    this.amount += amount;
  }
}
