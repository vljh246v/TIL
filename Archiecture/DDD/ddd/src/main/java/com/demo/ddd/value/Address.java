package com.demo.ddd.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Address {

    private final String address1;
    private final String address2;
    private final String zipcode;

}
