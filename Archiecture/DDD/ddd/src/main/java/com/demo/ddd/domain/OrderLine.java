package com.demo.ddd.domain;

import com.demo.ddd.value.Money;
import lombok.Getter;

@Getter
public class OrderLine {

    private final Product product;
    private final Money price; // 각 제품당 가격
    private final int quantity; // 수량
    private final Money amounts; // 총 합 가격

    public OrderLine(Product product, int price, int quantity) {
        this.product = product;
        this.price = new Money(price);
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return price.multiply(getQuantity());
    }
}
