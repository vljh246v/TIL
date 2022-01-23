package com.demo.ddd.order.domain.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Member {

    @EmbeddedId
    private MemberId id;

    private String name;

    protected Member() {
    }

    public Member(final MemberId id, final String name) {
        this.id = id;
        this.name = name;
    }
}
