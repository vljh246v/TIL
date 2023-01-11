package com.example.gof.behavioral.observer.java;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class MyEvent extends ApplicationEvent {

    private String message;

    public MyEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
