package com.example.gof.structural.flyweight.after;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public final class Font {
    private final String fontFamily;
    private final int fontSize;
}
