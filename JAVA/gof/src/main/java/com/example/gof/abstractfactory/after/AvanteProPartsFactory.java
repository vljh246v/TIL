package com.example.gof.abstractfactory.after;

import com.example.gof.abstractfactory.Engine;
import com.example.gof.abstractfactory.Wheel;

public class AvanteProPartsFactory implements CarPartsFactory{
    @Override
    public Engine createEngine() {
        return new AvanteProEngine();
    }

    @Override
    public Wheel createWheel() {
        return new AvanteProWheel();
    }
}
