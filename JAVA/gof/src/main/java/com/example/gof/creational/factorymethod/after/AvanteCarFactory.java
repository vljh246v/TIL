package com.example.gof.creational.factorymethod.after;

public class AvanteCarFactory extends DefaultCarFactory {
    @Override
    public Car createCar() {
        return new AvanteCar();
    }
}
