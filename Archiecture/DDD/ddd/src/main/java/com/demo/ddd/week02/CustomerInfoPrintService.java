package com.demo.ddd.week02;

import java.util.List;

public class CustomerInfoPrintService {

    private final SessionPrintEngine sessionPrintEngine;

    public CustomerInfoPrintService() {
        sessionPrintEngine = new SessionPrintEngine();
    }

    public void calculateDiscount(final List<String> name) {
        sessionPrintEngine.printClassAndSessionAndCustomerInfo("CalculateDiscountService", name);
    }
}
