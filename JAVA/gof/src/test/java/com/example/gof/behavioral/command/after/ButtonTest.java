package com.example.gof.behavioral.command.after;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ButtonTest {


    @Disabled(value = "dev test")
    @Test
    public void press() {
        Button button = new Button(new LightOffCommand(new Light()));
        button.press();
        button.press();

        button = new Button(new GameStartCommand(new Game()));
        button.press();
        button.press();
    }

}