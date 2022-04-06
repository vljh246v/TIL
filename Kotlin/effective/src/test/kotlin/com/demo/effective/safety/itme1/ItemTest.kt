package com.demo.effective.safety.itme1

import org.junit.jupiter.api.Test
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
    fun `coroutine test`() {

    }
}