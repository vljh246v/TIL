package com.demo.hexagonal.dto

import com.demo.hexagonal.domain.User

data class SaveUserDto(
    val content: UserInfo
) {
    fun toDomain(): User {
        return User(
            name = content.name,
            age = content.age,
            birthParam = content.birth
        )
    }
}

data class UserInfo(
    val name: String,
    val age: Int,
    val birth: String, // YYYY-MM-DD
)