package com.demo.boot.reactive.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItem {

    private Item item;
    private int quantity;

    CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }
    
}
