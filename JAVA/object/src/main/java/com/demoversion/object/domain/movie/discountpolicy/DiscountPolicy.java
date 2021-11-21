package com.demoversion.object.domain.movie.discountpolicy;

import com.demoversion.object.domain.movie.Money;
import com.demoversion.object.domain.movie.Screening;
import com.demoversion.object.domain.movie.discountcondition.DiscountCondition;
import java.util.Arrays;
import java.util.List;

public abstract class DiscountPolicy {

  private final List<DiscountCondition> conditions;

  public DiscountPolicy(final DiscountCondition... conditions) {
    this.conditions = Arrays.asList(conditions);
  }

  public Money calculateDiscountAmount(final Screening screening) {
    for (final DiscountCondition each : conditions) {
      if (each.isSatisfiedBy(screening)) {
        return getDiscountAmount(screening);
      }
    }
    return Money.ZERO;
  }

  abstract protected Money getDiscountAmount(Screening screening);
}
