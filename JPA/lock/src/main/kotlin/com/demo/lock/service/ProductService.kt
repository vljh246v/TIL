package com.demo.lock.service

import com.demo.lock.entity.Product
import com.demo.lock.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val repository: ProductRepository
) {
    @Transactional
    fun buy(id: Long) {
        val product = this.repository.findByIdOrNull(id)
        product?.decreaseStock(1)
    }

    @Transactional
    fun sell(id: Long) {
        val product = this.repository.findByIdOrNull(id)
        product?.decreaseStock(1)
    }

    @Transactional
    fun add(amount: Int) {
        repository.save(Product(stock = amount, version = 0))
    }
}
