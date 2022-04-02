package com.example.gof.factorymethod.after;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class ClientTest {

    @TestFactory
    Stream<DynamicTest> orderCar() {
        final String email = "vljh246v@naver.com";
        List<String> carName = Arrays.asList("avante", "k5");

        Stream<DynamicTest> orderCar =
                carName.stream()
                       .map(name -> DynamicTest.dynamicTest(
                               "car Nmae: " + name,
                               () -> {
                                   Car car = CarFactory.orderCar(name, email);
                                   assertThat(car.getName()).isEqualTo(name);
                                   System.out.println(car);
                               }
                               ));

       return orderCar;
    }

}