package com.demo.lock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LockApplication

fun main(args: Array<String>) {
    runApplication<LockApplication>(*args)
}
