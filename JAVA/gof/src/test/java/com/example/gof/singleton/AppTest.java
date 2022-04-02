package com.example.gof.singleton;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void eqSettingObject() {
        Settings settings1 = Settings.getInstance();
        Settings settings2 = Settings.getInstance();

        assertThat(settings1).isEqualTo(settings2);
    }

}