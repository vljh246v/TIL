package com.jaehyun.kotlin.study.study.ch04

data class Client(val name: String, val postalCode: Int)

class DelegatingCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList {}

fun main() {
    println("main")
    val client1 = Client("jaehyun", 123)
    println(client1)
    val client2 = Client("jaehyun", 123)

    println(client1 == client2)

    val hashSetOf = hashSetOf(client1)
    println(hashSetOf.contains(client2))
}
