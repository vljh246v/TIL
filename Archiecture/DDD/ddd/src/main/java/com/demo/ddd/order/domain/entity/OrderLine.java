package com.demo.ddd.order.domain.entity;

import com.demo.ddd.category.domain.product.Product;
import com.demo.ddd.common.model.Money;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class OrderLine {

    @Embedded
    private Product product;
    private Money price; // 각 제품당 가격
    private int quantity; // 수량
    private Money amounts; // 총 합 가격


    protected OrderLine(final Product product, final Money price, final int quantity, final Money amounts) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }


    private Money calculateAmounts() {
        return price.multiply(getQuantity());
    }
}
