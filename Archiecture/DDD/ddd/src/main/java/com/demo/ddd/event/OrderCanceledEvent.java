package com.demo.ddd.event;

import lombok.Getter;

@Getter
public class OrderCanceledEvent extends Event {

    private String orderNumber;
    public OrderCanceledEvent(String number) {
        super();
        this.orderNumber = number;
    }
}
