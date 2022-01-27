package com.demo.unittest.ch01;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void purchaseSucceedsWhenEnoughInventory_적() {

        // given
        Store store = new Store();
        store.addInventory(Product.Shampoo, 10);
        Customer customer = new Customer();

        // when
        boolean purchase = customer.purchase(store, Product.Shampoo, 5);

        assertThat(purchase).isTrue();
        assertThat(store.getInventory(Product.Shampoo)).isEqualTo(5);
    }

    @Test
    void purchaseFailsWhenNotEnoughInventory_detroit() {

        // given
        Store store = new Store();
        store.addInventory(Product.Shampoo, 10);
        Customer customer = new Customer();

        // when
        boolean purchase = customer.purchase(store, Product.Shampoo, 15);

        assertThat(purchase).isFalse();
        assertThat(store.getInventory(Product.Shampoo)).isEqualTo(10);
    }
}