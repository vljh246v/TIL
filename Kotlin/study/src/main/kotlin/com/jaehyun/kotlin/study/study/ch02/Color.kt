package com.jaehyun.kotlin.study.study.ch02

import com.jaehyun.kotlin.study.study.ch02.Color.*

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
    println(mixOptimized(RED, YELLOW))
}

fun mixOptimized(c1: Color, c2: Color) =
    when {
        (c1 == RED && c2 == YELLOW) ||
                (c1 == YELLOW && c2 == RED) -> ORANGE
        (c1 == YELLOW && c2 == BLUE) ||
                (c1 == BLUE && c2 == YELLOW) -> GREEN
        (c1 == BLUE && c2 == VIOLET) ||
                (c1 == VIOLET && c2 == BLUE) -> INDIGO
        else -> throw Exception("Dirty color")
    }

fun mix(c1: Color, c2: Color) =
    when(setOf(c1, c2)) {
        setOf(RED, YELLOW) -> ORANGE
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLET) -> INDIGO
        else -> throw Exception("Dirty color")
    }

fun getWarmth(color:Color) =
    when(color) {
        RED, ORANGE, YELLOW -> "warm"
        GREEN -> "neutral"
        BLUE, INDIGO, VIOLET -> "cold"
    }

fun getMnemonic(color:Color) =
    when(color) {
        RED -> "Richard"
        ORANGE -> "Of"
        YELLOW -> "York"
        GREEN -> "Gave"
        BLUE -> "Battle"
        INDIGO -> "In"
        VIOLET -> "Vain"
    }


