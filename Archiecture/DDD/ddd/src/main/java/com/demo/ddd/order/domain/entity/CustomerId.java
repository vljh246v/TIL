package com.demo.ddd.order.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@Embeddable
public class CustomerId implements Serializable {

    @Column(name = "customer_id")
    public Long id;
}
