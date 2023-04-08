package com.demo.sleuth

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class SleuthApplication

fun main(args: Array<String>) {
	runApplication<SleuthApplication>(*args)
}
