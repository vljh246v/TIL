package com.demo.lock.lock.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Version

@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var stock: Int,
    @Version
    var version: Int
) {
    fun decreaseStock(amount: Int) {
        this.stock -= amount
    }

    override fun toString(): String {
        return "Product(id=$id, stock=$stock)"
    }
}
