package com.demo.ddd.week01.value;

public class Money {

    private final int value;

    public Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public Money add(Money money) {
        return new Money(this.getValue() + money.getValue());
    }

    public Money multiply(int multiplier) {
        return new Money(this.getValue() * multiplier);
    }
}
