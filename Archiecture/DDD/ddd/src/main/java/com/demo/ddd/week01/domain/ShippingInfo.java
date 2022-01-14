package com.demo.ddd.week01.domain;

import com.demo.ddd.week01.value.Address;
import com.demo.ddd.week01.value.Receiver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShippingInfo {

    private final Receiver receiver;
    private final Address address;
}
