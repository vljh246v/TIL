package com.example.gof.abstractfactory.before;

import com.example.gof.factorymethod.after.AvanteCar;
import com.example.gof.factorymethod.after.Car;
import com.example.gof.factorymethod.after.DefaultCarFactory;

public class AvanteFactory extends DefaultCarFactory {

    @Override
    public Car createCar() {
        Car car = new AvanteCar();
        car.setEngine(new AvanteEngine());
        car.setWheel(new AvanteWheel());
        return car;
    }
}
