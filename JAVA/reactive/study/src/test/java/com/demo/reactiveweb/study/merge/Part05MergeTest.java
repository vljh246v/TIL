package com.demo.reactiveweb.study.merge;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class Part05MergeTest {

//========================================================================================

    // TODO Merge flux1 and flux2 values with interleave
    @Test
    public void mergeFluxWithInterleave() {
        Flux<Long> flux1 = Flux.interval(Duration.ofMillis(100)).take(10);
        Flux<Long> flux2 = Flux.just(100l, 101l, 102l);

        flux1.mergeWith(flux2)
                .doOnNext(System.out::println)
                .blockLast();
    }

//========================================================================================

    // TODO Merge flux1 and flux2 values with no interleave (flux1 values and then flux2 values)
    @Test
    public void mergeFluxWithNoInterleave() {
        Flux<Long> flux1 = Flux.interval(Duration.ofMillis(100)).take(10);
        Flux<Long> flux2 = Flux.just(100l, 101l, 102l);

        flux1.concatWith(flux2)
                .doOnNext(System.out::println)
                .blockLast();
    }

//========================================================================================

    // TODO Create a Flux containing the value of mono1 then the value of mono2
    @Test
    public void createFluxFromMultipleMono() {
        Mono<User> mono1 = Mono.just(new User("a", "b", "c"));
        Mono<User> mono2 = Mono.just(new User("d", "e", "f"));

        Flux<User> userFlux = mono1.mergeWith(mono2);

        Flux<User> concat = Flux.concat(mono1, mono2);
    }

    public class User{
        private String username;
        private String firstname;
        private String lastname;

        public User(String username, String firstname, String lastname){
            this.username = username;
            this.firstname = firstname;
            this.lastname = lastname;
        }
        public String getUsername(){
            return this.username;
        }
        public String getFirstname(){
            return this.firstname;
        }
        public String getLastname(){
            return this.lastname;
        }
    }
}