package com.example.gof.factorymethod.after;

public abstract class DefaultCarFactory implements CarFactory {

    @Override
    public void sendEmailTo(String email, Car car) {
        System.out.println(car.getName() + " 다 만들었습니다.");
    }
}
