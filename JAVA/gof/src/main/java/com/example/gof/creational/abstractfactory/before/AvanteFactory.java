package com.example.gof.creational.abstractfactory.before;

import com.example.gof.creational.factorymethod.after.AvanteCar;
import com.example.gof.creational.factorymethod.after.Car;
import com.example.gof.creational.factorymethod.after.DefaultCarFactory;

public class AvanteFactory extends DefaultCarFactory {

    @Override
    public Car createCar() {
        Car car = new AvanteCar();
        car.setEngine(new AvanteEngine());
        car.setWheel(new AvanteWheel());
        return car;
    }
}
