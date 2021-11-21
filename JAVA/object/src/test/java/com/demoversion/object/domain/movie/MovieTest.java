package com.demoversion.object.domain.movie;

import com.demoversion.object.domain.movie.discountcondition.PeriodCondition;
import com.demoversion.object.domain.movie.discountcondition.SequenceCondition;
import com.demoversion.object.domain.movie.discountpolicy.AmountDiscountPolicy;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class MovieTest {

  @Test
  void createMovie() {

    final LocalDateTime startTime = LocalDateTime.of(2021, 11, 22, 10, 30, 0);
    final Movie movie1 = new Movie("모가디슈", Duration.ofMinutes(120), Money.wons(10000),
        new AmountDiscountPolicy(Money.wons(800),
            new SequenceCondition(1),
            new SequenceCondition(11),
            new SequenceCondition(111),
            new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
            new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))));

    final Screening screening = new Screening(movie1, 11, startTime);
  }
}