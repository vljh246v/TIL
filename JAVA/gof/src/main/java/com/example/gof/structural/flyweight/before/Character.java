package com.example.gof.structural.flyweight.before;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Character {
    private final char value;
    private final String color;
    private final String fontFamily;
    private final int fontSize;
}
