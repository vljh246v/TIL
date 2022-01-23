package com.demo.ddd.order.domain.entity;

import com.demo.ddd.category.domain.product.ProductId;
import com.demo.ddd.common.model.Money;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
public class OrderLine {

    @Embedded
    private ProductId productId;

    @Column(name = "price")
    private Money price; // 각 제품당 가격

    @Column(name = "quantity")
    private int quantity; // 수량

    @Column(name = "amounts")
    private Money amounts; // 총 합 가격

    private OrderLine() {
    }

    public OrderLine(final ProductId productId, final Money price, final int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }


    private Money calculateAmounts() {
        return price.multiply(getQuantity());
    }
}
