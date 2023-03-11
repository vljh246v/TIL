package com.demo.hexagonal.adapter.out.persistence

import javax.persistence.*

@Entity
@Table(name = "user_info")
class UserEntity(
    @Id
    @Column(name = "id")
    val id: String,

    val name: String,
    val age: Int,
    val birthDay: String
)