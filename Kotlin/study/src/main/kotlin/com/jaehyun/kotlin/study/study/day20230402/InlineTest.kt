package com.jaehyun.kotlin.study.study.day20230402

import kotlin.system.measureTimeMillis

fun <T> first(list: List<T>): T {
    return list.first()
}

inline fun <T> inlineFirst(list: List<T>): T {
    return list.first()
}

inline fun <T> List<T>.myForEach(action: (T) -> Unit) {
    for (element in this) action(element)
}


fun main() {
    val list = (1..10000000).toList()
    val repeatCount = 1000000000

    val time1 = measureTimeMillis {
        repeat(repeatCount) {
            first(list)
        }
    }
    println("first: ${time1}ms")

    val time2 = measureTimeMillis {
        repeat(repeatCount) {
            inlineFirst(list)
        }
    }
    println("inlineFirst: ${time2}ms")
}