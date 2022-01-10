package com.demo.ddd.domain;

public class Money {

    private final int value;

    public Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
