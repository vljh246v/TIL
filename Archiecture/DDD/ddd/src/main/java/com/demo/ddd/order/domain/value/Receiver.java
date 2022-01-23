package com.demo.ddd.order.domain.value;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class Receiver {

    @Column(name = "receiver_name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Receiver(final String name, final String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    protected Receiver() {
    }
}
