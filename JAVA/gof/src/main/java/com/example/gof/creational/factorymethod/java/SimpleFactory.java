package com.example.gof.creational.factorymethod.java;

import com.example.gof.creational.factorymethod.after.AvanteCar;
import com.example.gof.creational.factorymethod.after.K5Car;

public class SimpleFactory {

    public Object createProduct(String name) {
        if (name.equals("avante")) {
            return new AvanteCar();
        } else if (name.equals("k5")) {
            return new K5Car();
        }
        throw new IllegalArgumentException("not support car");
    }
}
