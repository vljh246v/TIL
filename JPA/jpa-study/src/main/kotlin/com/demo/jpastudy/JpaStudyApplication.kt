package com.demo.jpastudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JpaStudyApplication

fun main(args: Array<String>) {
    runApplication<JpaStudyApplication>(*args)
}
