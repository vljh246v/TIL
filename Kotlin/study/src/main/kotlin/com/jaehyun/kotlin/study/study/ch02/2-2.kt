package com.jaehyun.kotlin.study.study.ch02

fun main(args: Array<String>) {
    val name = if (args.size > 0) args[0] else "Kotlin"
    println("Hello, $name")

    if (args.size > 0) {
        println("Hello, ${args[0]}")
    }
}