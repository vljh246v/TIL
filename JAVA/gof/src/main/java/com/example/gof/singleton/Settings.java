package com.example.gof.singleton;

public class Settings {
    private Settings() {}

    public static Settings getInstance() {
        return new Settings();
    }
}
