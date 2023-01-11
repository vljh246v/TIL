package com.example.gof.creational.factorymethod.after;

import com.example.gof.creational.abstractfactory.Engine;
import com.example.gof.creational.abstractfactory.Wheel;

import lombok.Data;

@Data
public class Car {

    private String name;

    private String color;

    private String logo;

    private Wheel wheel;

    private Engine engine;
}
