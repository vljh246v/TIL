package com.demo.ddd.order.domain.entity;

import com.demo.ddd.order.domain.value.Address;
import com.demo.ddd.order.domain.value.Receiver;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.Getter;

@Getter
@Embeddable
public class ShippingInfo {

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "order_name")),
        @AttributeOverride(name = "phoneNumber", column = @Column(name = "order_phone"))
    })
    private Receiver receiver;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipcode", column = @Column(name = "shipping_zipcode")),
        @AttributeOverride(name = "address1", column = @Column(name = "shipping_addr1")),
        @AttributeOverride(name = "address2", column = @Column(name = "shipping_addr2"))
    })
    private Address address;

    @Column(name = "shipping_message")
    private String message;

    public ShippingInfo() {
    }

    public ShippingInfo(final Receiver receiver, final Address address, final String message) {
        this.address = address;
        this.message = message;
        this.receiver = receiver;
    }
}
