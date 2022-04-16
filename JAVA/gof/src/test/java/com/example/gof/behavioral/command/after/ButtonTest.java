package com.example.gof.behavioral.command.after;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ButtonTest {


    @Disabled("dev test")
    @Test
    public void press() {
        Button button = new Button();
        button.press(new LightOnCommand(new Light()));
        button.press(new LightOffCommand(new Light()));

        button = new Button();
        button.press(new GameStartCommand(new Game()));
        button.press(new GameEndCommand(new Game()));
    }

    @Disabled("dev test")
    @Test
    public void undo() {
        Button button = new Button();
        button.press(new GameStartCommand(new Game()));
        button.press(new LightOnCommand(new Light()));
        button.undo();
        button.undo();
    }

}