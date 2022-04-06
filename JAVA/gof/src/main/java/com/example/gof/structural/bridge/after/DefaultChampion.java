package com.example.gof.structural.bridge.after;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultChampion implements Champion {
    private final Skin skin;
    private final String name;

    @Override
    public void move() {
        System.out.printf("%s %s move", skin.getName(), this.name);
    }

    @Override
    public void skillQ() {
        System.out.printf("%s %s Q", skin.getName(), this.name);
    }

    @Override
    public void skillW() {
        System.out.printf("%s %s W", skin.getName(), this.name);
    }

    @Override
    public void skillE() {
        System.out.printf("%s %s E", skin.getName(), this.name);
    }

    @Override
    public void skillR() {
        System.out.printf("%s %s R", skin.getName(), this.name);
    }
}
