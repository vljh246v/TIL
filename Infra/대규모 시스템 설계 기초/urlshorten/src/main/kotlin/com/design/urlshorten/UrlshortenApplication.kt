package com.design.urlshorten

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UrlshortenApplication

fun main(args: Array<String>) {
    runApplication<UrlshortenApplication>(*args)
}
