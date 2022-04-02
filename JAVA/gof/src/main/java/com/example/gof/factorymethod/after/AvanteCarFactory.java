package com.example.gof.factorymethod.after;

public class AvanteCarFactory implements CarFactory{
    @Override
    public Car createCar() {
        return new AvanteCar();
    }
}
