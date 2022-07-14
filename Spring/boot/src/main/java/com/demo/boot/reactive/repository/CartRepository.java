package com.demo.boot.reactive.repository;

import com.demo.boot.reactive.domain.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, String> {

}
