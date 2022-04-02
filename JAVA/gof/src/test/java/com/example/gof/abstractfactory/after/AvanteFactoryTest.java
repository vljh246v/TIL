package com.example.gof.abstractfactory.after;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.gof.factorymethod.after.CarFactory;

class AvanteFactoryTest {

    @Test
    void createCar() {

        CarFactory avanteFactory = new AvanteFactory(new AvantePartsFactory());

        assertThat(avanteFactory.createCar().getName()).isEqualTo("avante");
    }
}