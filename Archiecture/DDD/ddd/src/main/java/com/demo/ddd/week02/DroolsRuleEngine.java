package com.demo.ddd.week02;

import java.util.List;

public class DroolsRuleEngine {

    public void evaluate(final String sessionName, final List<String> facts) {
        facts.forEach(it -> System.out.println(this.getClass().getName() + ", " + sessionName + ", " + it));
    }
}
