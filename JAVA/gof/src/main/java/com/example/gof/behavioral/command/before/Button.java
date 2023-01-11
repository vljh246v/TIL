package com.example.gof.behavioral.command.before;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Button {

    private final Light light;

    public void press() {
        light.off();
    }

    public static void main(String[] args) {
        Button button = new Button(new Light());
        button.press();
        button.press();
        button.press();
        button.press();
    }
}
