package com.demo.hexagonal.adapter.out.persistence

import javax.persistence.*

@Entity
@Table(name = "user_info")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    val name: String,
    val age: Int,
    val birthDay: String
)