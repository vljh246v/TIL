package com.demoversion.object.domain.movie.discountpolicy;

import com.demoversion.object.domain.movie.Money;
import com.demoversion.object.domain.movie.Screening;

public class NoneDiscountPolicy extends DiscountPolicy {

  @Override
  protected Money getDiscountAmount(final Screening screening) {
    return Money.ZERO;
  }
}
