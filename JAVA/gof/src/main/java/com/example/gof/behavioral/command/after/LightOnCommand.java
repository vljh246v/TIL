package com.example.gof.behavioral.command.after;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LightOnCommand implements Command {

    private final Light light;

    @Override
    public void execute() {
        light.on();
    }
}
