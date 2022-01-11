package com.demo.ddd.domain;

import com.demo.ddd.value.Money;
import com.demo.ddd.value.OrderNo;
import java.util.List;
import lombok.Getter;

@Getter
public class Order {

    private OrderNo id;
    private List<OrderLine> orderLines;
    private int totalAmounts;
    private OrderStatus state;
    private ShippingInfo shippingInfo;


    public Order(List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if (!state.isShippingChangeable()) {
            throw new IllegalStateException("can't change shipping in " + state);
        }
        this.shippingInfo = newShippingInfo;
    }

    public void changeShipped() {
        this.state = OrderStatus.SHIPPED;
    }

    public void cancel() {
        verifyNotYetShipped();
        this.state = OrderStatus.CANCELED;
    }

    public void completePayment() {
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != Order.class) {
            return false;
        }

        Order other = (Order) obj;

        if (this.id == null) {
            return false;
        }

        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (shippingInfo == null) {
            throw new IllegalArgumentException("no ShippingInfo");
        }
        this.shippingInfo = shippingInfo;
    }

    private void calculateTotalAmounts() {

        this.totalAmounts = orderLines.stream()
            .map(OrderLine::getAmounts)
            .mapToInt(Money::getValue)
            .sum();
    }


    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    private void verifyNotYetShipped() {
        if (state != OrderStatus.PAYMENT_WAITING && state != OrderStatus.PREPARING) {
            throw new IllegalStateException("aleady shipped");
        }
    }

}
