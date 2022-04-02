package com.example.gof.singleton;

public class Settings {
    private Settings() {}

    private static final Settings INSTANCE = new Settings();

    public static Settings getInstance() {
        return INSTANCE;
    }
}
