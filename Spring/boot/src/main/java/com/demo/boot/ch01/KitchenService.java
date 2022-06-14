package com.demo.boot.ch01;

import reactor.core.publisher.Flux;

public class KitchenService {
    Flux<Dish> getDishes() {
        return Flux.just(
            new Dish("치킨"),
            new Dish("연어"),
            new Dish("스테이크")
        );
    }
}
