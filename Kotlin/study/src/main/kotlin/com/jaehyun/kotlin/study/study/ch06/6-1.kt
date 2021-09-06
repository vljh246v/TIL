package com.jaehyun.kotlin.study.study.ch06

fun strLenSafe (s: String?) : Int =
    if(s != null) s.length else 0

fun main() {
    val x: String? = null
    println(strLenSafe(x))
}