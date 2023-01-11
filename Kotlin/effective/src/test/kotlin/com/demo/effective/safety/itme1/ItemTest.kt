package com.demo.effective.safety.itme1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.concurrent.thread

internal class ItemTest {
    @Test
    fun bank() {
        var num = 0
        for (i in 1..1000) {
            thread {
                Thread.sleep(10)
                num += 1
            }
        }
        Thread.sleep(5000)
        print(num)
    }

    @Test
    fun readOnlyPropertyCollection() {
        val list = mutableListOf(1, 2, 3)
        val listSize = list.size
        list.add(4)
        assertThat(list.size).isNotEqualTo(listSize)
    }


    @TestFactory
    fun name(): List<DynamicTest> {
        data class Case(
            val firstName: String,
            val lastName: String
        )

        return listOf(
            Case("demo", "lim"),
        ).map { case ->
            DynamicTest.dynamicTest("Item - ${case}") {
                val item = Item(case.firstName, case.lastName)
                assertThat(item.fullName).isEqualTo(case.firstName + "." + case.lastName)
            }
        }
    }

}