package com.demo.lock.controller

import com.demo.lock.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(
    private val service: ProductService
) {

    @GetMapping("/test")
    fun test(): String {
        return "Test"
    }

    @GetMapping("/buy/{id}")
    fun buyProduct(@PathVariable id: Long) {
        service.buy(id)
    }

    @GetMapping("/sell/{id}")
    fun sellProduct(@PathVariable id: Long) {
        service.sell(id)
    }

    @GetMapping("/{amount}")
    fun addProduct(@PathVariable amount: Int) {
        service.add(amount)
    }
}