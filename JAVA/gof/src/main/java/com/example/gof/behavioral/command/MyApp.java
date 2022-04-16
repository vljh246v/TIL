package com.example.gof.behavioral.command;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyApp {

    private final Game game;

    public void press() {
        game.start();
    }

    public static void main(String[] args) {
        Button button = new Button(new Light());
        button.press();
        button.press();
        button.press();
        button.press();
    }
}
