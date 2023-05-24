package com.demo.hexagonal.domain

import java.text.ParseException
import java.text.SimpleDateFormat

class User(
    val name: String,
    val age: Int,
    birthParam: String, // YYYY-MM-DD
) {

    val birth: String
    init {
        try {
            val birthDateFormat = SimpleDateFormat("yyyy-MM-dd")
            birthDateFormat.isLenient = false
            birthDateFormat.parse(birthParam)
        } catch (e: ParseException) {
            throw IllegalArgumentException("Not support birthday date format!")
        }

        birth = birthParam
    }

    override fun toString(): String {
        return "name : $name, age : $age, birthDay : $birth"
    }
}