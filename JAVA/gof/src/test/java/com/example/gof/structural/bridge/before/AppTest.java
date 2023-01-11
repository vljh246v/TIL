package com.example.gof.structural.bridge.before;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void appTest() {
        Champion blackChampionOne = new BlackChampionOne();
        blackChampionOne.move();
        Champion whiteChampionOne = new WhiteChampionOne();
        whiteChampionOne.move();
    }

}