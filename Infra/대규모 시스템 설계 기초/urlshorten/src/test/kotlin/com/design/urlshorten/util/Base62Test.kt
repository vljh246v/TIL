package com.design.urlshorten.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Base62Test {

    @Test
    fun `encoding test to 0`() {
        val zero = Base62.encoding(0)
        assertThat(zero).isEqualTo("0")
    }

    @Test
    fun `encoding test to MAX_VALUE`() {
        val max = Base62.encoding(3521614606207)
        assertThat(max).isEqualTo("ZZZZZZZ")
    }
}
