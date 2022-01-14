package com.demo.ddd.order.domain.value;

public class Money {

    private final int value;

    public Money(final int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public Money add(final Money money) {
        return new Money(this.getValue() + money.getValue());
    }

    public Money multiply(final int multiplier) {
        return new Money(this.getValue() * multiplier);
    }
}
