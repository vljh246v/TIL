package com.demo.ddd.category.domain.product;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Embeddable
@Access(AccessType.FIELD)
public class ProductId implements Serializable {

    @Column(name = "product_id")
    private String id;

    protected ProductId() {
    }

    public ProductId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
