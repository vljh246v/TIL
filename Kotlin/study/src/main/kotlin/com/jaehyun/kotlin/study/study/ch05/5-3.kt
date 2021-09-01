package com.jaehyun.kotlin.study.study.ch05


fun main() {
    val people = listOf(Person("AA", 27), Person("BA", 31), Person("BC", 31), Person("DA", 25))

    println(people.map { it.name }.filter { it.startsWith("A") })

    val toList = people.asSequence()
        .map { it.name }
        .filter { it.startsWith("A") }
        .toList()
    println(toList)
}