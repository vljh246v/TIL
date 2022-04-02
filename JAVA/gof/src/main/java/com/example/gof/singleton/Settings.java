package com.example.gof.singleton;

public class Settings {
    private Settings() {}

    private static Settings instance;

    public static Settings getInstance() {
        if(instance == null) {
            instance = new Settings();
            return instance;
        }
        return instance;
    }
}
