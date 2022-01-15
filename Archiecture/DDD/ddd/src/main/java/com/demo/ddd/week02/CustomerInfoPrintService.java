package com.demo.ddd.week02;

import java.util.List;

public class CustomerInfoPrintService {

    private final Printer printer;

    public CustomerInfoPrintService(final Printer printer) {
        this.printer = printer;
    }

    public void printCustomerInfo(final List<String> name) {
        printer.print(name);
    }
}
