package com.demo.junit.day20220801

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class TestClassTest {

    @Test
    fun useFileIo() {
        val path = "src/test/resources"
        val file = File(path)
        val absolutePath = file.absolutePath

        println(absolutePath)
        assertTrue(absolutePath.endsWith(path))
    }
}