package com.example.gof.behavioral.memento.after;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class GameSave {

    private final int blueTeamScore;
    private final int redTeamScore;
}
