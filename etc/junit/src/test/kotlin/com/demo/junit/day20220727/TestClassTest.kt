package com.demo.junit.day20220727

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.function.ThrowingConsumer
import java.util.function.Function
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

    @TestFactory
    fun dynamicTestsFromStream(): Stream<DynamicTest> {

        val inputList: List<String> = listOf("https://www.naver.com", "https://www.google.com", "https://www.daum.net")
        val outputList: List<String> = listOf("23.220.128.185", "142.250.217.100", "211.249.220.24")

        val domainNameResolver = DomainNameResolver()

        val inputGenerator: Iterator<String> = inputList.iterator()
        val displayNameGenerator = Function<String, String> { name -> "Resolving: $name" }
        val testExecutor = ThrowingConsumer { input: String ->
            val index = inputList.indexOf(input)
            assertEquals(outputList[index], domainNameResolver.resolveDomain(input))
        }

        return DynamicTest.stream(
            inputGenerator, displayNameGenerator, testExecutor
        )
    }

    @TestFactory
    fun dynamicTestsFromStreamSimple(): Stream<DynamicTest> {
        val inputList: List<String> = listOf("https://www.naver.com", "https://www.google.com", "https://www.daum.net")
        val outputList: List<String> = listOf("23.220.128.185", "142.250.217.100", "211.249.220.24")

        val domainNameResolver = DomainNameResolver()

        return inputList.stream()
            .map {
                DynamicTest.dynamicTest("Resolving: $it") {
                    val id = inputList.indexOf(it)
                    assertEquals(outputList[id], domainNameResolver.resolveDomain(it))
                }
            }
    }

    @TestFactory
    fun dynamicTestsFromStreamCase(): Stream<DynamicTest> {
        class Case(
            val domain: String,
            val ip: String,
            val expected: Boolean
        )

        val domainNameResolver = DomainNameResolver()

        return listOf(
            Case("https://www.naver.com", "23.220.128.185", true),
            Case("https://www.google.com", "142.250.217.100", true),
            Case("https://www.daum.net", "wrong ip", false)
        ).stream().map {
            DynamicTest.dynamicTest("domain : $it") {
                assertEquals(it.expected, it.ip == domainNameResolver.resolveDomain(it.domain))
            }
        }
    }
}

class DomainNameResolver {
    private val ipByDomainName: MutableMap<String, String> = HashMap()

    init {
        ipByDomainName["https://www.naver.com"] = "23.220.128.185"
        ipByDomainName["https://www.google.com"] = "142.250.217.100"
        ipByDomainName["https://www.daum.net"] = "211.249.220.24"
    }
    fun resolveDomain(domainName: String): String? {
        return ipByDomainName[domainName]
    }
}
