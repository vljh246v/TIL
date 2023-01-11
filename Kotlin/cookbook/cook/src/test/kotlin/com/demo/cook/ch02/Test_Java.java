package com.demo.cook.ch02;

import static com.demo.cook.ch02.TestKt.addProduct;

import org.junit.jupiter.api.Test;

public class Test_Java {

    @Test
    void supplyAllArguments(){
        System.out.println(addProduct("Name", 5.0, "Desc"));
    }
}
