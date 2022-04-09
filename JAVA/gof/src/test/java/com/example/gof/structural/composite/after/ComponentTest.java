package com.example.gof.structural.composite.after;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class ComponentTest {

    @TestFactory
    Stream<DynamicTest> orderCar() {

        class Case {
            final Component component;
            final int expected;

            Case(Component component, int expected) {
                this.component = component;
                this.expected = expected;
            }
        }

        return Stream.of(new Case(new Item("item1", 100), 100),
                         new Case(new Bag(List.of(new Item("item1", 100), new Item("item2", 200))), 300)
                     )
                     .map(c -> DynamicTest.dynamicTest(
                             "car Nmae: " + c,
                             () -> assertThat(c.component.getPrice()).isEqualTo(c.expected)
                     ));
    }

}