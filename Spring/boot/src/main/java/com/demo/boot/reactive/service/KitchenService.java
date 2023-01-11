package com.demo.boot.reactive.service;

import com.demo.boot.ch01.Dish;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class KitchenService {

    private final List<Dish> menu = List.of(
        new Dish("chicken"),
        new Dish("noodles"),
        new Dish("beef")
    );

    private final Random picker = new Random();

    public Flux<Dish> getDishes() {
        return Flux.<Dish>generate(sink -> sink.next(randomDish()))
            .delayElements(Duration.ofMillis(getRandomTimeDuration()));
    }

    public long getRandomTimeDuration() {
        Random random = new Random();
        return random.longs(100L, 1000L)
            .findFirst()
            .orElse(250L);
    }

    private Dish randomDish() {
        return menu.get(picker.nextInt(menu.size()));
    }
}
