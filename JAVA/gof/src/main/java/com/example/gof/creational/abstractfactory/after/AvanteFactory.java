package com.example.gof.creational.abstractfactory.after;

import com.example.gof.creational.factorymethod.after.AvanteCar;
import com.example.gof.creational.factorymethod.after.Car;
import com.example.gof.creational.factorymethod.after.DefaultCarFactory;

public class AvanteFactory extends DefaultCarFactory {

    private final CarPartsFactory carPartsFactory;

    public AvanteFactory(CarPartsFactory carPartsFactory) {
        this.carPartsFactory = carPartsFactory;
    }

    @Override
    public Car createCar() {
        Car car = new AvanteCar();
        car.setEngine(carPartsFactory.createEngine());
        car.setWheel(carPartsFactory.createWheel());
        return car;
    }
}
