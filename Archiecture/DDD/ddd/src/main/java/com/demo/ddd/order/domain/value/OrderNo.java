package com.demo.ddd.order.domain.value;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class OrderNo implements Serializable {

    @Column(name = "order_number")
    private Long number;

    private OrderNo() {
    }

    public OrderNo(final Long number) {
        this.number = number;
    }
}
