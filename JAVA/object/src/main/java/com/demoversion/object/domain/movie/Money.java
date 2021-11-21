package com.demoversion.object.domain.movie;

import java.math.BigDecimal;

public class Money {

  public static final Money ZERO = Money.wons(0);
  private final BigDecimal amount;

  public Money(final BigDecimal amount) {
    this.amount = amount;
  }

  public static Money wons(final long amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public static Money wons(final double amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public Money plus(final Money amount) {
    return new Money(this.amount.add(amount.amount));
  }

  public Money minus(final Money amount) {
    return new Money(this.amount.subtract(amount.amount));
  }

  public Money times(final double percent) {
    return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
  }

  public boolean isLessThan(final Money other) {
    return amount.compareTo(other.amount) < 0;
  }

  public boolean isGreaterThanOrEqual(final Money other) {
    return amount.compareTo(other.amount) >= 0;
  }
}
