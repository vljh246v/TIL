package com.demoversion.object.domain.movie;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Screening {

  private final Movie movie;
  private final int sequence;
  private final LocalDateTime whenScreened;

  public LocalDateTime getStartTime() {
    return whenScreened;
  }

  public boolean isSequence(final int sequence) {
    return this.sequence == sequence;
  }

  public Money getMovieFee() {
    return movie.getFee();
  }

  public Reservation reserve(final Customer customer, final int audienceCount) {
    return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
  }

  private Money calculateFee(final int audienceCount) {
    return movie.calculateMovieFee(this).times(audienceCount);
  }
}
