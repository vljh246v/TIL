package com.example.gof.creational.factorymethod.after;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class ClientTest {

    @TestFactory
    Stream<DynamicTest> orderCar() {

        class Case {
            private final String carName;
            private final CarFactory carFactory;

            Case(String carName, CarFactory carFactory) {
                this.carName = carName;
                this.carFactory = carFactory;
            }
        }

        final String email = "vljh246v@naver.com";
        List<Case> carName = Arrays.asList(
                new Case("avante", new AvanteCarFactory()),
                new Case("k5", new K5CarFactory())
        );

        return carName.stream()
                      .map(c -> DynamicTest.dynamicTest(
                              "car Nmae: " + c.carName,
                              () -> {
                                  Car car = c.carFactory.orderCar(c.carName, email);
                                  assertThat(c.carName).isEqualTo(car.getName());
                                  System.out.println(car);
                              }
                      ));
    }

}