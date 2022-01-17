package com.demo.ddd.order.exception;

import com.demo.ddd.order.domain.value.OrderId;

public class NoOrderException extends RuntimeException {

    public NoOrderException(final OrderId orderNo) {
        super("Order information not found, OrderNo : " + orderNo);
    }
}
