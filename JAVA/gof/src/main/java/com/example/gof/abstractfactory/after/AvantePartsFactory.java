package com.example.gof.abstractfactory.after;

import com.example.gof.abstractfactory.Engine;
import com.example.gof.abstractfactory.Wheel;

public class AvantePartsFactory implements CarPartsFactory{
    @Override
    public Engine createEngine() {
        return new AvanteEngine();
    }

    @Override
    public Wheel createWheel() {
        return new AvanteWheel();
    }
}
