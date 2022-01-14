package com.demo.ddd.week01.domain;

import com.demo.ddd.week01.value.Money;
import lombok.Getter;

@Getter
public class OrderLine {

    private final Product product;
    private final Money price; // 각 제품당 가격
    private final int quantity; // 수량
    private final Money amounts; // 총 합 가격

    public OrderLine(Product product, Money price, int quantity) {
        this.product = product;
        this.price = new Money(price.getValue());
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return price.multiply(getQuantity());
    }
}
