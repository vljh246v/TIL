package com.example.gof.factorymethod.after;

import com.example.gof.abstractfactory.Engine;
import com.example.gof.abstractfactory.Wheel;

import lombok.Data;

@Data
public class Car {

    private String name;

    private String color;

    private String logo;

    private Wheel wheel;

    private Engine engine;
}
