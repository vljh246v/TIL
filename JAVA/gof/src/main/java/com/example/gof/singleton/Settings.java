package com.example.gof.singleton;

public class Settings {
    private Settings() {}

    private static Settings instance;

    public static synchronized Settings getInstance() {
        if(instance == null) {
            instance = new Settings();
        }
        return instance;
    }
}
