package com.demoversion.object.domain.movie.discountcondition;

import com.demoversion.object.domain.movie.Screening;

public class SequenceCondition implements DiscountCondition {

  private final int sequence;

  public SequenceCondition(final int sequence) {
    this.sequence = sequence;
  }

  @Override
  public boolean isSatisfiedBy(final Screening screening) {
    return screening.isSequence(sequence);
  }
}
