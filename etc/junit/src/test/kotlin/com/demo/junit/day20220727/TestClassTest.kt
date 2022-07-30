package com.demo.junit.day20220727

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.IntStream
import java.util.stream.Stream

internal class TestClassTest {
    @TestFactory
    fun dynamicTestWithCollection(): List<DynamicTest> {
        return listOf(
            DynamicTest.dynamicTest("Add test") {
                assertEquals(2, Math.addExact(1, 1))
            },
            DynamicTest.dynamicTest("Multiply Test") {
                assertEquals(4, Math.addExact(2, 2))
            }
        )
    }

    @TestFactory
    fun dynamicTestsWithIterable(): Iterable<DynamicTest> {
        return listOf(
            DynamicTest.dynamicTest("Add test") {
                assertEquals(2, Math.addExact(1, 1))
            },
            DynamicTest.dynamicTest("Multiply Test") {
                assertEquals(4, Math.addExact(2, 2))
            }
        )
    }

    @TestFactory
    fun dynamicTestsWithIterator(): Iterator<DynamicTest> {
        return listOf(
            DynamicTest.dynamicTest("Add test") {
                assertEquals(2, Math.addExact(1, 1))
            },
            DynamicTest.dynamicTest("Multiply Test") {
                assertEquals(4, Math.addExact(2, 2))
            }
        ).iterator()
    }

    @TestFactory
    fun dynamicTestsFromIntStream(): Stream<DynamicTest> {
        return IntStream.iterate(0) { n -> n + 2 }
            .limit(10)
            .mapToObj { n ->
                DynamicTest.dynamicTest("test $n") { assertTrue(n % 2 == 0) }
            }
    }
}
