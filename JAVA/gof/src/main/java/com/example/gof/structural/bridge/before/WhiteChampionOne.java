package com.example.gof.structural.bridge.before;

public class WhiteChampionOne implements Champion {
    @Override
    public void move() {
        System.out.println("WhiteChampionOne move");
    }

    @Override
    public void skillQ() {
        System.out.println("WhiteChampionOne Q");
    }

    @Override
    public void skillW() {
        System.out.println("WhiteChampionOne W");
    }

    @Override
    public void skillE() {
        System.out.println("WhiteChampionOne E");
    }

    @Override
    public void skillR() {
        System.out.println("WhiteChampionOne R");
    }
}
