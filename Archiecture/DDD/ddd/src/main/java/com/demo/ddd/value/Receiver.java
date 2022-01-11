package com.demo.ddd.value;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Receiver {

    private final String name;
    private final String phoneNumber;

}
