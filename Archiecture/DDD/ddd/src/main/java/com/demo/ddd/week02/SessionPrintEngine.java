package com.demo.ddd.week02;

import java.util.List;

public class SessionPrintEngine {

    public void printClassAndSessionAndCustomerInfo(final String sessionName, final List<String> names) {
        names.forEach(it -> System.out.println(this.getClass().getName() + ", " + sessionName + ", " + it));
    }
}
