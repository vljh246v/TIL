package com.demo.hexagonal.service

import com.demo.hexagonal.application.port.out.UserSavePort
import com.demo.hexagonal.domain.User
import org.springframework.stereotype.Service

@Service
class UserCommandService(
    private val userSavePort: UserSavePort
) {
    fun addUser(name: String, age: Int, birth: String) {

        userSavePort.saveUser(
            User(
                name = name,
                age = age,
                birthParam = birth
            )
        )
    }
}