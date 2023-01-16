package me.whiteship.refactoring._16_temporary_field._36_introduce_special_case;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    CustomerService customerService = new CustomerService();
    Customer unknown = new Customer("unknown", null, null);

    BillingPlan keesunBillingPlan = new BillingPlan();
    Customer keesun = new Customer("keesun", keesunBillingPlan, new PaymentHistory(1));

    @Test
    void customerName() {
        String unknownCustomerName = customerService.customerName(new Site(unknown));
        assertEquals("occupant", unknownCustomerName);

        String customer = customerService.customerName(new Site(keesun));
        assertEquals("keesun", customer);
    }

    @Test
    void billingPlan() {
        assertTrue((customerService.billingPlan(new Site(unknown)) instanceof BasicBillingPlan));

        assertEquals(keesunBillingPlan, customerService.billingPlan(new Site(keesun)));
    }

    @Test
    void weeksDelingquent() {
        assertEquals(1, customerService.weeksDelinquent(new Site(keesun)));

        assertEquals(0, customerService.weeksDelinquent(new Site(unknown)));
    }



}