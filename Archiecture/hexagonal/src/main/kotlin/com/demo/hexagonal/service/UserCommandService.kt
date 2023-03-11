package com.demo.hexagonal.service

import com.demo.hexagonal.adapter.out.persistence.UserEntity
import com.demo.hexagonal.repository.UserSaveRepository

class UserCommandService(
    private val userSaveRepository: UserSaveRepository
) {
    fun addUser(name: String, age: Int, birth: String) {
        userSaveRepository.save(UserEntity(
            name = name,
            age = age,
            birthDay = birth
        ))
    }
}