package com.demoversion.object.domain.movie;

public class Reservation {

  private final Customer customer;
  private final Screening screening;
  private final Money fee;
  private final int audienceCount;

  public Reservation(
      final Customer customer, final Screening screening, final Money fee,
      final int audienceCount) {
    this.customer = customer;
    this.screening = screening;
    this.fee = fee;
    this.audienceCount = audienceCount;
  }
}
