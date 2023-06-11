package com.demo.hexagonal.application.port.`in`

import com.demo.hexagonal.domain.User

interface UserCommandPort {
    fun addUser(user: User)
}