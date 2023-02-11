package com.demo.scubame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScubameApplication

fun main(args: Array<String>) {
    runApplication<ScubameApplication>(*args)
}
