package com.example.gof.behavioral.command.after;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameStartCommand implements Command {

    private final Game game;

    @Override
    public void execute() {
        game.start();
    }

    @Override
    public void undo() {
        new GameEndCommand(this.game).execute();
    }
}
