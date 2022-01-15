package com.demo.ddd.order.domain.entity;

import com.demo.ddd.order.domain.value.Address;
import com.demo.ddd.order.domain.value.Receiver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShippingInfo {

    private final Receiver receiver;
    private final Address address;
}
