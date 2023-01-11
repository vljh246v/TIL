package com.example.gof.structural.composite.after;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class Item implements Component {
    private final String name;
    private final int price;
}
