package com.demo.ddd.week02;

import java.util.List;

public class CustomerInfoPrintService {

    private final PrintEngine printEngine;

    public CustomerInfoPrintService() {
        printEngine = new PrintEngine();
    }

    public void calculateDiscount(final List<String> name) {
        printEngine.printClassAndSessionAndCustomerInfo("CalculateDiscountService", name);
    }
}
