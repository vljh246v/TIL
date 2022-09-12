package com.demo.cook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CookApplication

fun main(args: Array<String>) {
    runApplication<CookApplication>(*args)
}
