package com.example.gof.behavioral.memento.after;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    public void test() {
        int blueTeamScore = 10;
        int redTeamScore = 20;

        Game game = new Game();
        game.setBlueTeamScore(blueTeamScore);
        game.setRedTeamScore(redTeamScore);

        GameSave save = game.save();

        game.setBlueTeamScore(12);
        game.setRedTeamScore(22);

        game.restore(save);

        assertThat(game.getBlueTeamScore()).isEqualTo(blueTeamScore);
        assertThat(game.getRedTeamScore()).isEqualTo(redTeamScore);
    }
}