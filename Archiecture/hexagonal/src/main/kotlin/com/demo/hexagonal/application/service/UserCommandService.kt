package com.demo.hexagonal.application.service

import com.demo.hexagonal.application.port.`in`.UserCommandPort
import com.demo.hexagonal.domain.User
import org.springframework.stereotype.Service

//@Service
class UserCommandService: UserCommandPort {
    override fun addUser(user: User) {
        print(user)
    }
}