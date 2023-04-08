package com.demo.sleuth.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/example")
class ExampleController {
    private val log: Logger = LoggerFactory.getLogger(ExampleController::class.java)


    @GetMapping
    fun home(): String {
        log.info("Hello world!")
        return "Hello world!"
    }

}