package com.example.gof.factorymethod.before;

import org.springframework.util.StringUtils;

public class CarFactory {

    public static Car orderCar(String name, String email) {
        if (!StringUtils.hasLength(name)) {
            throw new IllegalArgumentException("자동차 이름을 지어주세요.");
        }
        if (!StringUtils.hasLength(email)) {
            throw new IllegalArgumentException("연락처를 남겨주세요.");
        }
        prepareFor(name);
        Car car = new Car();
        car.setName(name);

        // Customizing for specific name
        if (name.equalsIgnoreCase("avante")) {
            car.setLogo("\uD83D\uDE95");
        } else if (name.equalsIgnoreCase("k5")) {
            car.setLogo("\uD83D\uDE99");
        }

        // coloring
        if (name.equalsIgnoreCase("avante")) {
            car.setColor("red");
        } else if (name.equalsIgnoreCase("k5")) {
            car.setColor("balck");
        }

        // notify
        sendEmailTo(email, car);

        return car;
    }


    private static void prepareFor(String name) {
        System.out.println(name + " 만들 준비 중");
    }

    private static void sendEmailTo(String email, Car car) {
        System.out.println(car.getName() + " 다 만들었습니다.");
    }
}
