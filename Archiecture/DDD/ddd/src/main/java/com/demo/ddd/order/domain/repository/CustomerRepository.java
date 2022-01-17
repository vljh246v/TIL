package com.demo.ddd.order.domain.repository;

import com.demo.ddd.order.domain.entity.Customer;
import com.demo.ddd.order.domain.entity.CustomerId;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository {

    Customer findById(CustomerId id);
}
