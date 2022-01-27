package com.demo.unittest.ch01;

import java.util.HashMap;
import java.util.Map;

public class Store {

    private Map<Product, Integer> inventory = new HashMap<>();

    public boolean hasEnoughInventory(final Product product, final int quantity) {
        return getInventory(product) >= quantity;
    }

    public void removedInventory(final Product product, final int quantity) {
        if (!hasEnoughInventory(product, quantity)) {
            throw new IllegalArgumentException("Not enough inventory");
        }

        inventory.computeIfPresent(product, (key, value) -> value - quantity);
    }

    public void addInventory(final Product product, final int quantity) {
        inventory.merge(product, quantity, (key, value) -> value + quantity);
    }

    public int getInventory(final Product product) {
        return inventory.computeIfAbsent(product, k -> 0);
    }
}
