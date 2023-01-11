package com.example.gof.behavioral.command.after;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LightOffCommand implements Command {

    private final Light light;

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        new LightOnCommand(this.light).execute();
    }
}
