package com.demo.ddd.order.domain.value;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@Embeddable
public class Address {

    @Column(name = "address1")
    private final String address1;

    @Column(name = "address2")
    private final String address2;

    @Column(name = "zipcode")
    private final String zipcode;

}
