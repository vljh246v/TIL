package com.demo.ddd.week01.domain;

public enum OrderStatus {
    PAYMENT_WAITING {
        public boolean isShippingChangeable() {
            return true;
        }
    },
    PREPARING {
        public boolean isShippingChangeable() {
            return true;
        }
    },
    SHIPPED, DELIVERING, DELIVERY_COMPLETED, CANCELED;

    public boolean isShippingChangeable() {
        return false;
    }
}
