package com.demo.ddd.week02;

import java.util.List;

public class PrintEngine {

    public void printClassAndSessionAndCustomerInfo(final String sessionName, final List<String> facts) {
        facts.forEach(it -> System.out.println(this.getClass().getName() + ", " + sessionName + ", " + it));
    }
}
