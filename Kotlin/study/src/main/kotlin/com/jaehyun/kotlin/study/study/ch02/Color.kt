package com.jaehyun.kotlin.study.study.ch02

enum class Color (val r: Int, val g: Int, val b: Int) { // 상수의 프로퍼티를 정의
    RED(255, 0, 0), // 각 상수별 프로퍼티값을 지정
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLET(238, 130, 238); // 세미콜론이 꼭 필요하다.

    fun rgb() = (r * 256 + g) * 256 + b
}

fun main(args: Array<String>) {
    println(getWarmth(Color.ORANGE))
}

fun getWarmth(color:Color) =
    when(color) {
        Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
        Color.GREEN -> "neutral"
        Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
    }

fun getMnemonic(color:Color) =
    when(color) {
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "York"
        Color.GREEN -> "Gave"
        Color.BLUE -> "Battle"
        Color.INDIGO -> "In"
        Color.VIOLET -> "Vain"
    }
