package com.demo.hexagonal.application.port.out

import com.demo.hexagonal.domain.User

interface UserSavePort {
    fun saveUser(user: User)
}