package com.example.gof.singleton;

import org.junit.jupiter.api.Test;

class RuntimeExampleTest {


    @Test
    void runtime() {
        // not assert
        Runtime runtime = Runtime.getRuntime();

        System.out.println(runtime.maxMemory());
        System.out.println(runtime.freeMemory());
    }
}