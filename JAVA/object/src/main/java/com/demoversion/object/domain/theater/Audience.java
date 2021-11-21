package com.demoversion.object.domain.theater;

public class Audience {

  private final Bag bag;

  public Audience(final Bag bag) {
    this.bag = bag;
  }

  public Bag getBag() {
    return bag;
  }

  public Long buy(final Ticket ticket) {
    return bag.hold(ticket);
  }
}
