package com.demo.boot.ch01;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

public class KitchenService {
    public Flux<Dish> getDishes() {
        return Flux.just(
            new Dish("치킨"),
            new Dish("연어"),
            new Dish("스테이크")
        );
    }
}
