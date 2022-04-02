package com.example.gof.abstractfactory.after;

import com.example.gof.factorymethod.after.AvanteCar;
import com.example.gof.factorymethod.after.Car;
import com.example.gof.factorymethod.after.DefaultCarFactory;

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
