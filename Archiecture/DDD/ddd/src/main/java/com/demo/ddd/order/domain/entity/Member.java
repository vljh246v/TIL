package com.demo.ddd.order.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Getter;

@Getter
@Entity
@IdClass(MemberId.class)
public class Member {

    @Id
    @Column(name = "ID")
    private Long memberId;
}
