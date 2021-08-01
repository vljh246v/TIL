package com.demo.reactiveweb.study.transform;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Part04TransformTest {

    // TODO Capitalize the user username, firstname and lastname
    @Test
    public void capitalizeOne() {

        StepVerifier.create(Mono.just(new User("a", "b", "c"))
                    .map(u -> new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase())))
                .assertNext(u -> assertThat("ABC"));
    }

    // TODO Capitalize the users username, firstName and lastName
    @Test
    public void capitalizeMany() {

        StepVerifier.create(Flux.just(new User("a", "b", "c"))
                .map(u -> new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase())))
                .assertNext(u -> assertThat("ABC"));
    }

    // TODO Capitalize the users username, firstName and lastName using #asyncCapitalizeUser
    @Test
    public void asyncCapitalizeMany(Flux<User> flux) {
        StepVerifier.create(Flux.just(new User("a", "b", "c"))
                .flatMap(this::asyncCapitalizeUser))
                .assertNext(u -> assertThat("ABC"));
    }

    Mono<User> asyncCapitalizeUser(User u) {
        return Mono.just(new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase()));
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