package com.example.gof.behavioral.memento.before;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game implements Serializable {

    private int redTeamScore;
    private int blueTeamScore;
}
