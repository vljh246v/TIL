package com.example.gof.factorymethod.after;

public class K5CarFactory extends DefaultCarFactory{
    @Override
    public Car createCar() {
        return new K5Car();
    }
}
