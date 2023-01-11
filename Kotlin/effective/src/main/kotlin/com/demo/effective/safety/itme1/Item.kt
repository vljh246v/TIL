package com.demo.effective.safety.itme1

class Item(var firstName: String, var lastName: String) {
    val fullName
        get() = "$firstName.$lastName"
}