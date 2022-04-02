package com.example.gof.singleton;


import static org.assertj.core.api.Assertions.assertThat;

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
}