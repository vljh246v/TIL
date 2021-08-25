package com.jaehyun.kotlin.study.study.ch04

class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client(name=$name, postalCode=$postalCode)"
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client)
            return false
        return name == other.name && postalCode == other.postalCode
    }

    override fun hashCode(): Int = name.hashCode() * 31 + postalCode
}

fun main() {
    println("main")
    val client1 = Client("jaehyun", 123)
    println(client1)
    val client2 = Client("jaehyun", 123)

    println(client1 == client2)

    val hashSetOf = hashSetOf(client1)
    println(hashSetOf.contains(client2))

}
