package com.demo.cook.ch03

class Test

class Task(
    _priority: Int = DEFAULT_PRIORITY
) {
    companion object {
        const val MIN_PRIORITY = 1
        const val MAX_PRIORITY = 5
        const val DEFAULT_PRIORITY = 3
    }

    val isLowPriority
        get() = priority < 3

    var priority = validPriority(_priority)
        set(value) {
            field = validPriority(value)
        }

    private fun validPriority(p: Int) = p.coerceIn(MIN_PRIORITY, MAX_PRIORITY)
}

data class Product(
    val name: String,
    val price: Double,
    val onSale: Boolean = false
)

data class OrderItem(
    val product: Product,
    val quantity: Int
)

class Customer(val name: String) {
    private var _messages: List<String>? = null

    val messages: List<String> by lazy { loadMessages() }
    private fun loadMessages(): MutableList<String>{
        return mutableListOf(
            "test1", "test2", "test3"
        ).also { println("Loaded message") }
    }
}

data class Point(val x: Int, val y: Int)
operator fun Point.unaryMinus() = Point(-x, -y)

