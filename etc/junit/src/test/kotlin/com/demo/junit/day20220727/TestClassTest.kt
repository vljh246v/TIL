package com.demo.junit.day20220727

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.function.Executable

internal class TestClassTest {
    @TestFactory
    fun dynamicTestWithCollection(): List<DynamicTest> {
        return listOf(
            DynamicTest.dynamicTest("Add test") {
                assertEquals(4, Math.addExact(1, 1))
            }
        )
    }
}