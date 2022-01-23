package com.demo.ddd.order.service;

import com.demo.ddd.order.domain.entity.Order;
import com.demo.ddd.order.domain.entity.ShippingInfo;
import com.demo.ddd.order.domain.repository.OrderRepository;
import com.demo.ddd.order.domain.value.OrderNo;
import com.demo.ddd.order.exception.NoOrderException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangeOrderService {

    private final OrderRepository orderRepository;

    public void changeShippingInfo(final OrderNo orderNo, final ShippingInfo newShippingInfo, final boolean useNewShippingAddressAsMemberAddr) {
        final Order order = orderRepository.findById(orderNo);

        if (order == null) {
            throw new NoOrderException(orderNo);
        }
    }

}
