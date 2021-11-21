package com.demoversion.object.domain.movie.discountcondition;

import com.demoversion.object.domain.movie.Screening;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PeriodCondition implements DiscountCondition {

  private DayOfWeek dayOfWeek;
  private LocalTime startTime;
  private LocalTime endTime;

  @Override
  public boolean isSatisfiedBy(final Screening screening) {
    return screening.getStartTime().getDayOfWeek().equals(dayOfWeek) &&
        startTime.compareTo(screening.getStartTime().toLocalTime()) <= 0 &&
        endTime.compareTo(screening.getStartTime().toLocalTime()) >= 0;
  }
}
