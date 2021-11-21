package com.demoversion.object.domain.movie.discountcondition;

import com.demoversion.object.domain.movie.Screening;

public interface DiscountCondition {

  boolean isSatisfiedBy(final Screening screening);
}
