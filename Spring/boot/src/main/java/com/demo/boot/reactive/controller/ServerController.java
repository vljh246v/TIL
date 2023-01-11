package com.demo.boot.reactive.controller;

import com.demo.boot.ch01.Dish;
import com.demo.boot.reactive.service.KitchenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ServerController {

    private final KitchenService kitchen;

    public ServerController(KitchenService kitchen) {
        this.kitchen = kitchen;
    }

    @GetMapping(value = "/server", produces = "text/event-stream;charset=UTF-8")
    Flux<Dish> serveDishes() {
        return this.kitchen.getDishes();
    }


    @GetMapping(value = "/server-dishes", produces = "text/event-stream;charset=UTF-8")
    Flux<Dish> deliverDishes() {
        return this.kitchen.getDishes()
            .map(Dish::deliver);
    }
}
