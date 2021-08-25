package com.jaehyun.kotlin.study.study.ch04

class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client(name=$name, postalCode=$postalCode)"
}

fun main() {
    println("main")
    println(Client("jaehyun", 123))
}
