package com.demoversion.object.domain.movie;

import com.demoversion.object.domain.movie.discountpolicy.DiscountPolicy;
import java.time.Duration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Movie {

  private final String title;
  private final Duration runningTime;
  private final Money fee;
  private final DiscountPolicy discountPolicy;


  public Money getFee() {
    return fee;
  }

  public Money calculateMovieFee(final Screening screening) {
    return fee.minus(discountPolicy.calculateDiscountAmount(screening));
  }
}
