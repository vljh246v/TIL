package com.example.gof.factorymethod.after;

import org.springframework.util.StringUtils;

public interface CarFactory {

    default Car orderCar(String name, String email) {
        validate(name, email);
        prepareFor(name);
        Car car = createCar();
        sendEmailTo(email, car);
        return car;
    }

    Car createCar();

    private void validate(final String name, final String email) {
        if (!StringUtils.hasLength(name)) {
            throw new IllegalArgumentException("자동차 이름을 지어주세요.");
        }
        if (!StringUtils.hasLength(email)) {
            throw new IllegalArgumentException("연락처를 남겨주세요.");
        }
    }

    private static void prepareFor(String name) {
        System.out.println(name + " 만들 준비 중");
    }
    private static void sendEmailTo(String email, Car car) {
        System.out.println(car.getName() + " 다 만들었습니다.");
    }
}
