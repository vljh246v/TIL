package com.example.gof.behavioral.command.after;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameEndCommand implements Command {

    private final Game game;

    @Override
    public void execute() {
        game.end();
    }
}
