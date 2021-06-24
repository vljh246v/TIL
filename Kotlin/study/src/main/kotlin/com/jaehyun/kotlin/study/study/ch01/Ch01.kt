package com.jaehyun.kotlin.study.study.ch01

fun main(args: Array<String>){
    val persons = listOf(Person("영희"), Person("철수", age=29))
    val oldest = persons.maxByOrNull { it.age ?: 0 }

    println("나이가 가장 많은 사람: $oldest")

}