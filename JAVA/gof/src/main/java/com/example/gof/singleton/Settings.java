package com.example.gof.singleton;

import java.io.Serializable;

public class Settings implements Serializable {

    private static class SettingsHolder {
        private static final Settings INSTANCE = new Settings();
    }
    private Settings() {}

    public static Settings getInstance() {
        return SettingsHolder.INSTANCE;
    }
}
