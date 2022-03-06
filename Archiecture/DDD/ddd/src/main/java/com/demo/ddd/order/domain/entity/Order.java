package com.demo.ddd.order.domain.entity;

import com.demo.ddd.common.converter.MoneyConverter;
import com.demo.ddd.common.model.Money;
import com.demo.ddd.event.Events;
import com.demo.ddd.event.OrderCanceledEvent;
import com.demo.ddd.order.domain.value.OrderNo;
import com.demo.ddd.order.domain.value.OrderState;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "purchase_order")
@Access(AccessType.FIELD)
public class Order {

    @EmbeddedId
    private OrderNo id;

    @Embedded
    private Orderer orderer;

    @ElementCollection
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_number"))
    @OrderColumn(name = "line_idx")
    private List<OrderLine> orderLines;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "total_amounts")
    private Money totalAmounts;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Embedded
    private ShippingInfo shippingInfo;


    public Order() {
    }

    public Order(final List<OrderLine> orderLines, final ShippingInfo shippingInfo) {
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    public void changeShippingInfo(final ShippingInfo newShippingInfo) {
        if (!state.isShippingChangeable()) {
            throw new IllegalStateException("can't change shipping in " + state);
        }
        this.shippingInfo = newShippingInfo;
    }

    public void changeShipped() {
        this.state = OrderState.SHIPPED;
    }

    public void cancel() {
        verifyNotYetShipped();
        this.state = OrderState.CANCELED;
        Events.raise(new OrderCanceledEvent(this.id.getNumber().toString()));
    }

    public void completePayment() {
    }

    public void setOrderLines(final List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != Order.class) {
            return false;
        }

        final Order other = (Order) obj;

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


    private void setShippingInfo(final ShippingInfo shippingInfo) {
        if (shippingInfo == null) {
            throw new IllegalArgumentException("no ShippingInfo");
        }
        this.shippingInfo = shippingInfo;
    }

    private void calculateTotalAmounts() {

        final int sum = orderLines.stream()
            .map(OrderLine::getAmounts)
            .mapToInt(Money::getValue)
            .sum();

        this.totalAmounts = new Money(sum);
    }


    private void verifyAtLeastOneOrMoreOrderLines(final List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    private void verifyNotYetShipped() {
        if (state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING) {
            throw new IllegalStateException("aleady shipped");
        }
    }

}
