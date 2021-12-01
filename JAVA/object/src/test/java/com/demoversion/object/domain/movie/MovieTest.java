package com.demoversion.object.domain.movie;

import static org.assertj.core.api.Assertions.assertThat;

import com.demoversion.object.domain.movie.discountcondition.PeriodCondition;
import com.demoversion.object.domain.movie.discountcondition.SequenceCondition;
import com.demoversion.object.domain.movie.discountpolicy.AmountDiscountPolicy;
import com.demoversion.object.domain.movie.discountpolicy.DiscountPolicy;
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

    assertThat(movie1).isNotNull();
  }

  @Test
  void getFee() {
    final Money fee = Money.wons(1000L);
    final Movie movie = new Movie(null, null, fee, null);

    assertThat(movie.getFee()).isEqualTo(fee);
  }

  @Test
  void calculateMovieFee() {
    final LocalDateTime startTime = LocalDateTime.of(2021, 11, 22, 10, 0, 0);
    final Money fee = Money.wons(1000L);
    final DiscountPolicy discountPolicy = new AmountDiscountPolicy(Money.wons(800),
        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59)));
    final Movie movie = new Movie("모가디슈", null, fee, discountPolicy);

    final Screening screening = new Screening(movie, 11, startTime);

    final Money discountFee = movie.calculateMovieFee(screening);

    assertThat(discountFee).isEqualTo(fee.minus(discountPolicy.calculateDiscountAmount(screening)));
  }
}