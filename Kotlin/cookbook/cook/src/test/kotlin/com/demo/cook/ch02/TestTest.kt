package com.demo.cook.ch02

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TestTest {

    @Test
    fun `check all overloads`() {
        assertAll("Overloads called from Kotlin",
            { println(addProduct("Nmme", 5.0, "Desc")) },
            { println(addProduct("Nmme", 5.0,)) },
            { println(addProduct("Nmme")) }
        )
    }

    @Test
    fun typeChange() {
        val intVar: Int = 3
        val longVar: Long = intVar.toLong()
    }

    @Test
    fun baseTest(){
        assertTrue(43.toString(2) == "101011")

        for (radix in (Character.MIN_RADIX..Character.MAX_RADIX)) {
            println("$radix : ${42.toString(radix)}")
        }
    }

    @Test
    fun `raise to power`(){
        assertAll(
            { assertThat(1, equalTo(2 `**` 0 )) },
            { assertThat(2, equalTo(2 `**` 1 )) },
            { assertThat(4, equalTo(2 `**` 2 )) },


            { assertThat(4L, equalTo(2L `**` 2 )) },


            { assertThat(4F, equalTo(2F `**` 2 )) },
        )
    }

    @Test
    fun `doubling and halving`() {
        assertAll("left shifts doubling form 1",
            { assertThat(2, equalTo(1 shl 1)) }, // 0000_0001
            { assertThat(4, equalTo(1 shl 2)) }, // 0000_0010
            { assertThat(128, equalTo(1 shl 7)) }, // // 1000_0000
        )

        assertAll("right shifts having form 235",
            { assertThat(117, equalTo(235 shr 1)) }, // 1110_1011
            { assertThat(58, equalTo(235 shr 2)) }, // 0111_0101
            { assertThat(29, equalTo(235 shr 3)) }, // 0011_1010
            { assertThat(1, equalTo(235 shr 7)) }, // 0000_0001
        )
    }
}