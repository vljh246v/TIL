package me.whiteship.refactoring._06_mutable_data._19_separate_query_from_modifier;

public class EmailGateway {
    public void send(String bill) {
        System.out.println(bill);
    }
}
