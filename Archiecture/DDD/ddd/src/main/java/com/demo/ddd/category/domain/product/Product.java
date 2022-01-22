package com.demo.ddd.category.domain.product;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Embeddable
@Entity
@Table(name = "product")
public class Product {

    @EmbeddedId
    private ProductId id;
}
