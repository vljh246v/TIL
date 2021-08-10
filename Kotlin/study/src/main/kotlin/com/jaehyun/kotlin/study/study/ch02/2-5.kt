package com.jaehyun.kotlin.study.study.ch02

import java.io.BufferedReader
import java.io.StringReader

fun test(number: Int){

    val percentage =
        if(number in 0..100)
            number
        else
            throw IllegalArgumentException("oh!")

    println(percentage)
}

fun readNumber(reader: BufferedReader) {

    val number = try {
        Integer.parseInt(reader.readLine())
    }catch (e: NumberFormatException) {
        null
    }

    println(number)


//    return try{
//        val line = reader.readLine()
//        return Integer.parseInt(line)
//    }catch (e: NumberFormatException) {
//        null
//    }finally {
//        reader.close()
//    }
}

fun main(args: Array<String>) {
    val reader = BufferedReader(StringReader("eeee"))
    readNumber(reader)
//    test(2000);
}