package com.demo.ddd.week02;

import java.util.List;

public class SessionPrinter implements Printer {

    private final SessionPrintEngine sessionPrintEngine;

    public SessionPrinter() {
        this.sessionPrintEngine = new SessionPrintEngine();
    }

    public void print(final List<String> names) {
        sessionPrintEngine.printClassAndSessionAndCustomerInfo("CalculateDiscountService", names);
    }
}
