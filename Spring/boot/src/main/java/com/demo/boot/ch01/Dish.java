package com.demo.boot.ch01;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Dish {

    private final String dishName;
    private boolean delivered = false;

    public Dish(String dishName) {
        this.dishName = dishName;
    }

    public static Dish deliver(Dish dish) {
        dish.setDelivered(true);
        return dish;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
