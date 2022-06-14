package com.demo.boot.ch01;

import reactor.core.publisher.Flux;

public class PoliteServer {

    private final KitchenService kitchenService;

    public PoliteServer(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    Flux<Dish> doingMyJob(){
       return this.kitchenService.getDishes()
           .doOnNext(dish -> System.out.println("Thank you for " + dish + "!"))
           .doOnError(error -> System.out.println("So sorry about " + error.getMessage()))
           .doOnComplete(() -> System.out.println("Thank for all your hard work!"))
           .map(Dish::deliver);
    }

}
