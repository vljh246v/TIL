package com.demo.cook.ch03

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class TaskTest {

    @Test
    fun createTask() {
        val myTask = Task().apply { priority = 4 }
        println(myTask.priority)
    }

    @Test
    fun `check equivalence`() {
        val p1 = Product("baseball", 10.0)
        val p2 = Product("baseball", 10.0, false)

        assertEquals(p1, p2)
        assertEquals(p1.hashCode(), p2.hashCode())
    }

    @Test
    fun `create set to check equals and hashcode`() {
        val p1 = Product("baseball", 10.0)
        val p2 = Product(price = 10.0, onSale = false, name = "baseball")

        val products = setOf(p1, p2)
        assertEquals(1, products.size)
    }

    @Test
    fun `change price using copy`() {
        val p1 = Product("baseball", 10.0)
        val p2 = p1.copy(price = 12.0)
        assertAll(
            { assertEquals("baseball", p2.name) },
            { assertThat(p2.price).isCloseTo(12.0, Offset.offset(0.1)) },
            { assertFalse(p2.onSale) }
        )
    }

    @Test
    fun `data copy function is shallow`() {
        val item1 = OrderItem(Product("baseball", 10.0), 5)
        val item2 = item1.copy()

        assertAll(
            { assertTrue(item1 == item2) },
            { assertFalse(item1 === item2) },
            { assertTrue(item1.product == item2.product) },
            { assertTrue(item1.product === item2.product) }
        )
    }

    @Test
    fun `destructure using component functions`() {
        val p = Product("baseball", 10.0)

        val (name, price, sale) = p
        assertAll(
            { assertThat(p.name).isEqualTo(name) },
            { assertThat(p.price).isCloseTo(price, Offset.offset(0.1)) },
            { assertThat(p.onSale).isFalse() }
        )
    }

    @Test
    fun `load messages`() {
        val customer = Customer("Fred").apply { messages }
        assertEquals(3, customer.messages.size)
    }

    @Test
    fun `unary minus point`() {
        val point = Point(10, 20)
        assertEquals(point.unaryMinus(), Point(-10, -20))
    }


}
