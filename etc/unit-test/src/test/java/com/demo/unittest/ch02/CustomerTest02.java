package com.demo.unittest.ch02;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.demo.unittest.ch01.Customer;
import com.demo.unittest.ch01.Product;
import com.demo.unittest.ch01.Store;

class CustomerTest02 {

    @Test
    void purchaseSucceedsWhenEnoughInventory_detroit() {

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
    void purchaseSucceedsWhenEnoughInventory_mockist() {

        // given
        Store storeMock = mock(Store.class);
        when(storeMock.hasEnoughInventory(Product.Shampoo, 5)).thenReturn(true);
        Customer customer = new Customer();

        // when
        boolean purchase = customer.purchase(storeMock, Product.Shampoo, 5);

        assertThat(purchase).isTrue();
        verify(storeMock, times(1)).removedInventory(Product.Shampoo, 5);
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

    @Test
    void purchaseFailsWhenNotEnoughInventory_mockist() {

        // given
        Store storeMock = mock(Store.class);
        when(storeMock.hasEnoughInventory(Product.Shampoo, 5)).thenReturn(false);
        Customer customer = new Customer();

        // when
        boolean purchase = customer.purchase(storeMock, Product.Shampoo, 5);

        assertThat(purchase).isFalse();
        verify(storeMock, never()).removedInventory(Product.Shampoo, 5);
    }
}