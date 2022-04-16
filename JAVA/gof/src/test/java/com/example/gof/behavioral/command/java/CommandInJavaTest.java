package com.example.gof.behavioral.command.java;

import com.example.gof.behavioral.command.after.Game;
import com.example.gof.behavioral.command.after.Light;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class CommandInJavaTest {

    @Test
    public void test1() {
        Light light = new Light();
        Game game = new Game();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(light::on);
        executorService.submit(light::off);
        executorService.submit(game::start);
        executorService.submit(game::end);
    }

}