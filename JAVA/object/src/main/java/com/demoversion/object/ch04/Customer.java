package com.demoversion.object.ch04;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Customer {

    private final String name;
    private final String id;
}
