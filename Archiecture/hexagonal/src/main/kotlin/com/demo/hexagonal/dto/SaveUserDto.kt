package com.demo.hexagonal.dto

data class SaveUserDto(
    val content: User

)

data class User(
    val name: String,
    val age: Int,
    val birth: String, // YYYY-MM-DD
)