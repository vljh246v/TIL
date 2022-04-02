package com.example.gof.factorymethod.after;

public class K5CarFactory implements CarFactory{
    @Override
    public Car createCar() {
        return new K5Car();
    }
}
