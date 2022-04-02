package com.example.gof.factorymethod.java;

import com.example.gof.factorymethod.after.AvanteCar;
import com.example.gof.factorymethod.after.K5Car;

public class SimpleFactory {

    public Object createProduct(String name) {
        if(name.equals("avante")) {
            return new AvanteCar();
        } else if(name.equals("k5")) {
            return new K5Car();
        }
        throw new IllegalArgumentException("not support car");
    }
}
