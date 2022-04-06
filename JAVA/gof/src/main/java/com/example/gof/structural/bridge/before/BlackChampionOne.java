package com.example.gof.structural.bridge.before;

public class BlackChampionOne implements Champion {
    @Override
    public void move() {
        System.out.println("BlackChampionOne move");
    }

    @Override
    public void skillQ() {
        System.out.println("BlackChampionOne Q");
    }

    @Override
    public void skillW() {
        System.out.println("BlackChampionOne W");
    }

    @Override
    public void skillE() {
        System.out.println("BlackChampionOne E");
    }

    @Override
    public void skillR() {
        System.out.println("BlackChampionOne R");
    }
}
