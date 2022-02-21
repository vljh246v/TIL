package com.demo.ddd.order.service;

import com.demo.ddd.event.Events;
import com.demo.ddd.event.OrderCanceledEvent;
import com.demo.ddd.order.domain.entity.Order;
import com.demo.ddd.order.domain.repository.OrderRepository;
import com.demo.ddd.order.domain.value.OrderNo;
import com.demo.ddd.order.exception.NoOrderException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class CancelOrderService {

    private final OrderRepository orderRepository;
    private final RefundService refundService;

    public CancelOrderService(OrderRepository orderRepository, RefundService refundService) {
        this.orderRepository = orderRepository;
        this.refundService = refundService;
    }
3
    @Transactional
    public void cancel(final OrderNo orderNo) {

        Events.handle(
                (OrderCanceledEvent evt) -> refundService.refund(evt.getOrderNumber())
        );


        final Order order = orderRepository.findById(orderNo);
        if (order == null) {
            throw new NoOrderException(orderNo);
        }
        order.cancel();

        Events.reset();
    }
}
