package com.demo.boot.ch01;

import reactor.core.publisher.Flux;

public class SimpleService {
    private final KitchenService kitchenService;

    public SimpleService(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    public Flux<Dish> doingMyJob(){
        return this.kitchenService.getDishes()
            .map(Dish::deliver);
    }

}
