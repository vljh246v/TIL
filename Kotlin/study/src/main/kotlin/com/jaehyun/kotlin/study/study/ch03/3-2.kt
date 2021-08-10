package com.jaehyun.kotlin.study.study.ch03

import java.lang.StringBuilder

fun main(args: Array<String>){
//    val list = listOf(1, 2, 3)
//    println(list)

    val list = listOf(1, 2, 3)
    println(joinToString(list, "; ", "(", ")"))
}

fun <T> joinToString(
    collections: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String
) : String {
    val result = StringBuilder(prefix)

    for((index, element) in collections.withIndex()){
        if(index > 0) result.append(separator)
        result.append(element)
    }

    result.append(postfix)
    return result.toString()
}