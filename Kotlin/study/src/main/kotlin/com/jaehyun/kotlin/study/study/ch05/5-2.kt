package com.jaehyun.kotlin.study.study.ch05

val canBeInClub27 = { p: Person -> p.age <= 27 }

fun main() {
//    val list = listOf(1, 2, 3, 4)
//    println(list.filter { it % 2 == 0 })
//    println(list.map { it * it })
//
//    val numbers = mapOf(0 to "zero", 1 to "one")
//    println(numbers.mapValues { it.value.uppercase() })
    val people = listOf(Person("A", 27), Person("B", 31))
    println(people.all(canBeInClub27))
    println(people.any(canBeInClub27))
    println(people.count(canBeInClub27))
    println(people.find(canBeInClub27))
}