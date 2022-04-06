package com.example.gof.structural.bridge.after;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void appTest() {
        Champion blackOne = new BlackChampionOne(new Black());
        blackOne.move();
    }

}