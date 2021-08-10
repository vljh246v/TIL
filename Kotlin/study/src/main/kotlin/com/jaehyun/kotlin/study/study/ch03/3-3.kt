package com.jaehyun.kotlin.study.study.ch03

import strings.joinToString


fun String.lastChar() : Char = this.get(this.length - 1)

val String.lastChar: Char
    get() = get(length - 1)


var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value) {
        this.setCharAt(length - 1, value)
    }

fun main() {
//    val test = "1234"
//
//    println(test.lastChar())
//
//    val list = listOf(1, 2, 3)
//    println(list.joinToString(separator = "; ", prefix = "(", postfix = ")"))

//    val view: View = Button()
//    view.click()

    println("Kotlin".lastChar)
    val sb = StringBuilder("Kotlin?")
    sb.lastChar = '!'
    println(sb)

}

open class View {
    open fun click() = println("View Clicked")
}

open class Button: View() {
    override fun click() {
        println("Button clicked")
    }
}