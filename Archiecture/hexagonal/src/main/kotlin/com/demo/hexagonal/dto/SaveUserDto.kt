package com.demo.hexagonal.dto

data class SaveUserDto(
    val name: String,
    val age: Int,
    val birth: String, // YYYY-MM-DD
)