package com.example.gof.creational.factorymethod.java;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class CalenderExampleTest {

    @Test
    void getCalenderType() {
        assertThat(Calendar.getInstance().getClass().getName()).isEqualTo(java.util.GregorianCalendar.class.getName());
        assertThat(Calendar.getInstance(Locale.forLanguageTag("ja-JP-x-lvariant-JP")).getClass().getName()).isEqualTo("java.util.JapaneseImperialCalendar");

    }

}