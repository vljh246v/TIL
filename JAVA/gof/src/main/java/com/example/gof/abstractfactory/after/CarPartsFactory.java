package com.example.gof.abstractfactory.after;

import com.example.gof.abstractfactory.Engine;
import com.example.gof.abstractfactory.Wheel;

public interface CarPartsFactory {
    Engine createEngine();
    Wheel createWheel();
}
