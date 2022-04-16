package com.example.gof.behavioral.command.after;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Button {

    private final Command command;

    public void press() {
        command.execute();
    }
}
