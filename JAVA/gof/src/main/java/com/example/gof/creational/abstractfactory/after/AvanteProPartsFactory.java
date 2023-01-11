package com.example.gof.creational.abstractfactory.after;

import com.example.gof.creational.abstractfactory.Engine;
import com.example.gof.creational.abstractfactory.Wheel;

public class AvanteProPartsFactory implements CarPartsFactory {
    @Override
    public Engine createEngine() {
        return new AvanteProEngine();
    }

    @Override
    public Wheel createWheel() {
        return new AvanteProWheel();
    }
}
