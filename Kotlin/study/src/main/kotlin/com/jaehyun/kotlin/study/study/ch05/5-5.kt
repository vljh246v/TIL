package com.jaehyun.kotlin.study.study.ch05

fun alphabet1(): String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}

fun alphabet2(): String {
    val stringBuilder = StringBuilder()
    return with(stringBuilder) {
        for (letter in 'A'..'Z') {
            this.append(letter)
        }
        append("\nNow I know the alphabet!")
        this.toString()
    }
}

fun alphabet3() = with(StringBuilder()) {
    for (letter in 'A'..'Z') {
        this.append(letter)
    }
    append("\nNow I know the alphabet!")
    this.toString()
}

fun alphabet4() = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        this.append(letter)
    }
    append("\nNow I know the alphabet!")
}.toString()

fun main() {

    println(alphabet1())
    println(alphabet2())
    println(alphabet3())
    println(alphabet4())
}