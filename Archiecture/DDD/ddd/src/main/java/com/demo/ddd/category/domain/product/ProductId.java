package com.demo.ddd.category.domain.product;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductId implements Serializable {

    @Column(name = "product_id")
    private String id;
}
