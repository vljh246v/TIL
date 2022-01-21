package com.demo.ddd.order.domain.entity;

import com.demo.ddd.order.domain.value.Address;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Getter;

@Getter
@Entity
@IdClass(CustomerId.class)
public class Customer {

    @Id
    @Column(name = "ID")
    private Long customerId;
    
    private Address address;

    public void changeAddress(final Address address) {
        this.address = address;
    }
}
