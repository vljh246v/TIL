package com.demoversion.object.ch04;

import com.demoversion.object.domain.movie.Money;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Movie {

  private final String title;
  private final Duration runningTime;
  private final Money fee;
  private final List<DiscountCondition> discountConditions = new ArrayList<>();
  private final MovieType movieType;
  private final Money discountAmount;
  private final double discountPercent;

  public List<DiscountCondition> getDiscountConditions() {
    return Collections.unmodifiableList(discountConditions);
  }
}
