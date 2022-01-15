package com.demo.ddd.order.service;

import com.demo.ddd.order.domain.entity.Order;
import com.demo.ddd.order.domain.repository.OrderRepository;
import com.demo.ddd.order.domain.value.OrderNo;
import com.demo.ddd.order.exception.NoOrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderService {

    @Autowired
    OrderRepository orderRepository;

    public void cancel(final OrderNo orderNo) {
        final Order order = orderRepository.findByNumber(orderNo);
        if (order == null) {
            throw new NoOrderException(orderNo);
        }
        order.cancel();
    }
}
