package com.demo.ddd.domain;

import com.demo.ddd.value.Address;
import com.demo.ddd.value.Receiver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShippingInfo {

    private final Receiver receiver;
    private final Address address;
}
