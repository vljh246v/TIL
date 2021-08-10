package com.jaehyun.kotlin.study.study.ch03

fun main(args: Array<String>) {
    val set = hashSetOf(1, 7, 53)
    val list = arrayListOf(1, 7, 54)
    val map = hashMapOf(1 to "One", 7 to "Seven", 53 to "Fifty-Three")

    val strings = listOf("first", "second", "fourteenth")
    println(strings.last())

    val numbers = setOf(1, 14, 2)
    println(numbers.maxOrNull())


}