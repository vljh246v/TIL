package com.demo.ddd.order.service;

import org.springframework.stereotype.Service;

@Service
public interface RefundService {
    void refund(String orderNumber);
}
