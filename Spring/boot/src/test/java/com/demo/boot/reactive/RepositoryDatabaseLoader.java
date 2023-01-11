package com.demo.boot.reactive;

import com.demo.boot.reactive.domain.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class RepositoryDatabaseLoader {

    @Bean
    CommandLineRunner initialize(MongoOperations operations) {
        return args -> {
            operations.save(new Item("Alf alarm clock", 19.99));
            operations.save(new Item("Smurf TV tray", 24.99));
        };
    }
}