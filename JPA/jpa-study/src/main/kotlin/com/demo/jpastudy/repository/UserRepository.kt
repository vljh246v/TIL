package com.demo.jpastudy.repository

import com.demo.jpastudy.entity.AddressEntity
import com.demo.jpastudy.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByName(name: String): UserEntity?
}
interface AddressRepository : JpaRepository<AddressEntity, Long> {
    fun findByAddress(address: String): AddressEntity
}
