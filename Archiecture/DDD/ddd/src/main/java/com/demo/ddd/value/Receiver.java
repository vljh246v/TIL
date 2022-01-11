package com.demo.ddd.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Receiver {

    private final String name;
    private final String phoneNumber;

}
