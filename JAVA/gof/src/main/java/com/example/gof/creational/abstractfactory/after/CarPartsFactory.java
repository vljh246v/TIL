package com.example.gof.creational.abstractfactory.after;

import com.example.gof.creational.abstractfactory.Engine;
import com.example.gof.creational.abstractfactory.Wheel;

public interface CarPartsFactory {
    Engine createEngine();

    Wheel createWheel();
}
