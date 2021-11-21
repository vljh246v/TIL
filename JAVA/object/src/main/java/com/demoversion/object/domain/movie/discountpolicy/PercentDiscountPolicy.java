package com.demoversion.object.domain.movie.discountpolicy;

import com.demoversion.object.domain.movie.Money;
import com.demoversion.object.domain.movie.Screening;
import com.demoversion.object.domain.movie.discountcondition.DiscountCondition;

public class PercentDiscountPolicy extends DiscountPolicy {

  private final double percent;

  public PercentDiscountPolicy(final double percent, final DiscountCondition... conditions) {
    super(conditions);
    this.percent = percent;
  }

  @Override
  protected Money getDiscountAmount(final Screening screening) {
    return screening.getMovieFee().times(percent);
  }
}
