package com.demo.ddd.week01.value;

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
