package com.example.gof.creational.after;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.gof.creational.abstractfactory.after.AvanteFactory;
import com.example.gof.creational.abstractfactory.after.AvantePartsFactory;
import com.example.gof.creational.factorymethod.after.CarFactory;

class AvanteFactoryTest {

    @Test
    void createCar() {

        CarFactory avanteFactory = new AvanteFactory(new AvantePartsFactory());

        assertThat(avanteFactory.createCar().getName()).isEqualTo("avante");
    }
}