package com.example.gof.behavioral.command.after;

import java.util.Stack;

public class Button {

    private final Stack<Command> commands = new Stack<>();


    public void press(Command command) {
        command.execute();
        commands.push(command);
    }

    public void undo() {
        if (!commands.isEmpty()) {
            Command command = commands.pop();
            command.undo();
        }
    }
}
