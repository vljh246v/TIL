package com.demo.ddd.order.domain.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Entity
public class Member {

    @EmbeddedId
    private MemberId memberId;
}
