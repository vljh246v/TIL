package com.demo.ddd.order.exception;

import com.demo.ddd.order.domain.value.OrderNo;

public class NoOrderException extends RuntimeException {

    public NoOrderException(final OrderNo orderNo) {
        super("Order information not found, OrderNo : " + orderNo);
    }
}
