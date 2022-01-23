package com.demo.ddd.order.domain.repository;

import com.demo.ddd.order.domain.entity.Order;
import com.demo.ddd.order.domain.entity.ShippingInfo;
import com.demo.ddd.order.domain.value.Address;
import com.demo.ddd.order.domain.value.OrderNo;
import com.demo.ddd.order.domain.value.Receiver;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DummyOrderRepository implements OrderRepository {

    @Override
    public Order findById(final OrderNo orderNo) {
        return new Order(List.of(), new ShippingInfo(new Receiver("name", "phoneNumber"), new Address("address1", "address2", "zipCode"), "message"));
    }
}
