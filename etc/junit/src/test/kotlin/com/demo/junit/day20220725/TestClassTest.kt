package com.demo.junit.day20220725

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class TestClassTest {


    @Test
    fun givenNumbers_whenOddCheck_thenVerify1(testInfo: TestInfo) {
        println("displayName = " + testInfo.displayName)
        val number = 5
        assertTrue(TestClass().oddCheck(number))
    }

    var testInfo: TestInfo? = null

    @BeforeEach
    fun init(testInfo: TestInfo) {
        this.testInfo = testInfo
    }

    @ParameterizedTest(name = "givenNumbers_whenOddCheck_thenVerify{0}")
    @ValueSource(ints =[1, 3, 5, -3, 15])
    fun givenNumbers_whenOddCheck_thenVerify2(number: Int) {
        println("displayName = " + testInfo!!.displayName)
        assertTrue(TestClass().oddCheck(number))
    }
}