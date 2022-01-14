package com.demo.ddd.week02;

import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerInfoPrintServiceTest {
    
    @Test
    void printCustomerInfo() {

        final Printer stubPrinter = names -> names.forEach(System.out::println);
        final CustomerInfoPrintService customerInfoPrintService = new CustomerInfoPrintService(stubPrinter);

        customerInfoPrintService.printCustomerInfo(List.of("demo1", "demo2"));
    }
}