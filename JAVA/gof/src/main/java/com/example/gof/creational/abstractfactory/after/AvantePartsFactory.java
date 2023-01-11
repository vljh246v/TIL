package com.example.gof.creational.abstractfactory.after;

import com.example.gof.creational.abstractfactory.Engine;
import com.example.gof.creational.abstractfactory.Wheel;

public class AvantePartsFactory implements CarPartsFactory {
    @Override
    public Engine createEngine() {
        return new AvanteEngine();
    }

    @Override
    public Wheel createWheel() {
        return new AvanteWheel();
    }
}
