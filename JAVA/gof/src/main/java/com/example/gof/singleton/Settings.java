package com.example.gof.singleton;

public class Settings {
    private Settings() {}

    private static volatile Settings instance;

    public static Settings getInstance() {
        if(instance == null) {
            synchronized (Settings.class) {
                if(instance == null) {
                    instance = new Settings();
                }
            }
        }
        return instance;
    }
}
