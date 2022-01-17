package com.demo.ddd.order.domain.repository;

import com.demo.ddd.order.domain.entity.Order;
import com.demo.ddd.order.domain.value.OrderId;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    Order findById(OrderId orderId);
}
