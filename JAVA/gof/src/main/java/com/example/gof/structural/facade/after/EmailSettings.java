package com.example.gof.structural.facade.after;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailSettings {

    private final String host;

    public String getHost() {
        return this.host;
    }
}
