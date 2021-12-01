package com.demoversion.object.domain.movie;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void wons() {
        final long amount = 1234L;
        final Money wons = Money.wons(amount);

        assertThat(wons.getAmount()).isEqualTo(BigDecimal.valueOf(amount));
    }

    @Test
    void testWons() {

        final double amount = 1234D;
        final Money wons = Money.wons(amount);

        assertThat(wons.getAmount()).isEqualTo(BigDecimal.valueOf(amount));
    }

    @Test
    void plus() {
        final long amount1 = 1234L;
        final long amount2 = 1234L;
        final Money wons = Money.wons(amount1).plus(Money.wons(amount2));

        assertThat(wons.getAmount()).isEqualTo(
            BigDecimal.valueOf(amount1).add(BigDecimal.valueOf(amount2)));
    }

    @Test
    void minus() {
        final long amount1 = 1234L;
        final long amount2 = 1234L;
        final Money wons = Money.wons(amount1).minus(Money.wons(amount2));

        assertThat(wons.getAmount()).isEqualTo(
            BigDecimal.valueOf(amount1).subtract(BigDecimal.valueOf(amount2)));
    }

    @Test
    void times() {
        final long amount1 = 1234L;
        final Money wons = Money.wons(amount1).times(10D);

        assertThat(wons.getAmount()).isEqualTo(
            BigDecimal.valueOf(amount1).multiply(BigDecimal.valueOf(10D)));
    }

    @Test
    void isLessThan() {
        final long amount1 = 1234L;
        final long amount2 = 5678L;
        final Money wons1 = Money.wons(amount1);
        final Money wons2 = Money.wons(amount2);

        assertThat(wons1.isLessThan(wons2)).isTrue();
    }

    @Test
    void isGreaterThanOrEqual() {
        final long amount1 = 1234L;
        final long amount2 = 5678L;
        final Money wons1 = Money.wons(amount1);
        final Money wons2 = Money.wons(amount2);

        assertThat(wons1.isGreaterThanOrEqual(wons2)).isFalse();
    }
}