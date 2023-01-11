package com.example.gof.behavioral.observer.after;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User implements Subscriber {
    private final String name;

    @Override
    public void handleMessage(String message) {
        System.out.println(message);
    }
}
