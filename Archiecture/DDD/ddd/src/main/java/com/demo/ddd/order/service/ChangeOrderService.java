package com.demo.ddd.order.service;

import com.demo.ddd.order.domain.entity.Customer;
import com.demo.ddd.order.domain.entity.Order;
import com.demo.ddd.order.domain.entity.ShippingInfo;
import com.demo.ddd.order.domain.repository.CustomerRepository;
import com.demo.ddd.order.domain.repository.OrderRepository;
import com.demo.ddd.order.domain.value.OrderId;
import com.demo.ddd.order.exception.NoOrderException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChangeOrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public void changeShippingInfo(final OrderId orderId, final ShippingInfo newShippingInfo, final boolean useNewShippingAddressAsMemberAddr) {
        final Order order = orderRepository.findById(orderId);

        if (order == null) {
            throw new NoOrderException(orderId);
        }

        if (useNewShippingAddressAsMemberAddr) {
            final Customer customer = customerRepository.findById(order.getOrderer().getCustomerId());
            customer.changeAddress(newShippingInfo.getAddress());
        }
    }

}
