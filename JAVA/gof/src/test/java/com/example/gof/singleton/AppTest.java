package com.example.gof.singleton;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void eqSettingObject() {
        Settings settings1 = Settings.getInstance();
        Settings settings2 = Settings.getInstance();

        assertThat(settings1).isEqualTo(settings2);
    }

    @Test
    void notEqSettingObjectUseToReflection() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Settings settings1 = Settings.getInstance();
        Constructor<Settings> constructor = Settings.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Settings settings2 = constructor.newInstance();

        assertThat(settings1).isNotEqualTo(settings2);
    }

    @Test
    void test() throws IOException, ClassNotFoundException {
        Settings settings1 = Settings.getInstance();
        Settings settings2;

        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("settings.obj"))) {
            out.writeObject(settings1);
        }

        try (ObjectInput in = new ObjectInputStream(new FileInputStream("settings.obj"))) {
            settings2 = (Settings) in.readObject();
        }

        assertThat(settings1).isNotEqualTo(settings2);
    }
}