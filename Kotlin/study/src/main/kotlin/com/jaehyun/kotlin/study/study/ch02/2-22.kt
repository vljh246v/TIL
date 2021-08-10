package com.jaehyun.kotlin.study.study.ch02

import java.util.*

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}

fun isLetter(c: Char) = when {
    c in 'a'..'z' || c in 'A'..'Z' -> true
    else -> false
}

fun isNotDigit(c:Char) = c !in '0'..'9'


fun recognize(c: Char) = when(c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know"
}

fun main(args: Array<String>){

    println(recognize('8'))
//    println(isLetter('q'))
//    println(isNotDigit('x'))

//    for(i in  1..100) {
//        print(fizzBuzz(i))
//    }

//    for(i in 100 downTo 1 step 2) {
//        print(fizzBuzz(i))
//    }

//    val binaryReps = TreeMap<Char, String>()
//
//    for(c in 'A'..'F') {
//        val binary = Integer.toBinaryString(c.code)
//        binaryReps[c] = binary;
//    }
//
//    for((letter, binary) in binaryReps) {
//        println("$letter = $binary")
//    }

}


