package com.demoversion.object.domain.movie.discountpolicy;

import com.demoversion.object.domain.movie.Money;
import com.demoversion.object.domain.movie.Screening;
import com.demoversion.object.domain.movie.discountcondition.DiscountCondition;

public class AmountDiscountPolicy extends DiscountPolicy {

  private final Money discountAmount;

  public AmountDiscountPolicy(final Money discountAmount, final DiscountCondition... conditions) {
    super(conditions);
    this.discountAmount = discountAmount;
  }

  @Override
  protected Money getDiscountAmount(final Screening screening) {
    return discountAmount;
  }
}
