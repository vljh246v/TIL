package com.demo.junit.day20220726


import org.junit.jupiter.api.Test

internal class TestClassTest {


    @Test
    fun sortTreeShouldReturnEvergreen_WhenPineIsPassed_UseJUnit5() {
        val tree = TestClass().sortTree("Pine")
        org.junit.jupiter.api.Assertions.assertEquals(tree.javaClass, Evergreen::class.java)
    }

    @Test
    fun sortTreeShouldReturnEvergreen_WhenPineIsPassed_UseHamcrest() {
        val tree = TestClass().sortTree("Pine")
//        org.hamcrest.MatcherAssert.assertThat(tree, org.hamcrest.Matchers.instanceOf(Evergreen::class.java))
        org.hamcrest.MatcherAssert.assertThat(tree, org.hamcrest.Matchers.isA(Evergreen::class.java))
    }

    @Test
    fun sortTreeShouldReturnEvergreen_WhenPineIsPassed_UseAssertJ() {
        val tree = TestClass().sortTree("Pine")
//        org.assertj.core.api.Assertions.assertThat(tree).isExactlyInstanceOf(Evergreen::class.java)
        org.assertj.core.api.Assertions.assertThat(tree).hasSameClassAs(Evergreen("Pine"))
    }


}