package com.example.gof.factorymethod.after;

public class AvanteCarFactory extends DefaultCarFactory{
    @Override
    public Car createCar() {
        return new AvanteCar();
    }
}
