package com.demo.ddd.common.model;

public class Money {

    private final int value;

    public Money(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Money multiply(final int multiplier) {
        return new Money(value * multiplier);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}