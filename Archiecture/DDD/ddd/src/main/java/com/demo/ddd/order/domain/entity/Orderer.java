package com.demo.ddd.order.domain.entity;

import lombok.Getter;

@Getter
public class Orderer {

    private MemberId memberId;
    private CustomerId customerId;
    private String name;

}
