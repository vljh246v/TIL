package com.demo.reactiveweb.study.stepverifier;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Part03StepVerifierTest {

    @Test
    public void expectFooBarComplete() {

        Flux<String> flux = Flux.just("foo", "bar");

        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .expectComplete()
                .verify();
    }

    @Test
    public void expectFooBarError() {

        Flux<String> flux = Flux.just("foo", "bar");

        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .verifyError(RuntimeException.class);
    }

    @Test
    public void expectSkylerJesseComplete() {
        Flux<User> flux = Flux.just(new User("swhite"), new User("jpinkman"));

        StepVerifier.create(flux)
                .assertNext(u -> assertThat(u.getUsername()).isEqualTo("swhite"))
                .assertNext(u -> assertThat(u.getUsername()).isEqualTo("jpinkman"))
                .expectComplete()
                .verify();
    }

    @Test
    public void expect10Elements() {
        Flux<Long> take = Flux.interval(Duration.ofMillis(100))
                .take(10);

        StepVerifier.create(take)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    void expect3600Elements() {
        Supplier<Flux<Long>> supplier = () -> Flux.interval(Duration.ofSeconds(1))
                .take(3600);
        StepVerifier.withVirtualTime(supplier)
                .thenAwait(Duration.ofHours(1))
                .expectNextCount(3600)
                .expectComplete()
                .verify();
    }

    public class User{
        private String username;

        public User(String username){
            this.username = username;
        }
        public String getUsername(){
            return username;
        }
    }

}
