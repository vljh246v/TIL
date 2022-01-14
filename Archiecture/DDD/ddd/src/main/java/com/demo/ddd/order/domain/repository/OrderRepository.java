package com.demo.ddd.order.domain.repository;

import com.demo.ddd.order.domain.entity.Order;
import com.demo.ddd.order.domain.value.OrderNo;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    Order findByNumber(OrderNo orderNo);
}
