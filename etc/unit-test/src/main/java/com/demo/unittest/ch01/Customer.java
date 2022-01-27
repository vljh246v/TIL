package com.demo.unittest.ch01;

public class Customer {

    public boolean purchase(final Store store, final Product product, final int quantity) {
        if (!store.hasEnoughInventory(product, quantity)) {
            return false;
        }

        store.removedInventory(product, quantity);
        return true;
    }
}
