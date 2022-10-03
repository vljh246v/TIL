package com.demo.jpastudy.entity

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Table(name = "user")
@Entity
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    val name: String? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val addressList: MutableList<AddressEntity> = mutableListOf()
) {
    override fun toString(): String {
        return "UserEntity(id = $id, name = $name, addressList = $addressList)"
    }
}

@Table(name = "address")
@Entity
class AddressEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @Column(name = "address")
    val address: String? = null
) {
    override fun toString(): String {
        return "AddressEntity(id = $id, address = $address)"
    }
}
