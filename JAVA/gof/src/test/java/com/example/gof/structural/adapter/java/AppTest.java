package com.example.gof.structural.adapter.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void arrays() {
        List<String> strings = Arrays.asList("A", "B", "C");
        System.out.println(strings);
        Enumeration<String> enumeration = Collections.enumeration(strings);
        System.out.println(enumeration);
        ArrayList<String> list = Collections.list(enumeration);
        
    }

}