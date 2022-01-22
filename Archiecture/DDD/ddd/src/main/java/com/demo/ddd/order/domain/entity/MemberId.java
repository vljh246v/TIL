package com.demo.ddd.order.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class MemberId implements Serializable {

    @Column(name = "member_id")
    private Long id;

    protected MemberId() {
    }

    public MemberId(final Long id) {
        this.id = id;
    }
}
