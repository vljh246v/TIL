package com.example.gof.creational.factorymethod.after;

public class K5CarFactory extends DefaultCarFactory {
    @Override
    public Car createCar() {
        return new K5Car();
    }
}
