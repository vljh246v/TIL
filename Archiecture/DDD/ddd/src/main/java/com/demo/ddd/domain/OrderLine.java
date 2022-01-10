package com.demo.ddd.domain;

import lombok.Getter;

@Getter
public class OrderLine {

    private final Product product;
    private final int price;
    private final int quantity;
    private int amounts;

    public OrderLine(Product product, int price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    private int calculateAmounts() {
        return price * quantity;
    }
}
