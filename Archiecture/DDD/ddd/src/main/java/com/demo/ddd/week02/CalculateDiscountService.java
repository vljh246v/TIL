package com.demo.ddd.week02;

import java.util.List;

public class CalculateDiscountService {

    private final DroolsRuleEngine droolsRuleEngine;

    public CalculateDiscountService() {
        droolsRuleEngine = new DroolsRuleEngine();
    }

    public void calculateDiscount(final List<String> name) {
        droolsRuleEngine.evaluate("CalculateDiscountService", name);
    }
}
