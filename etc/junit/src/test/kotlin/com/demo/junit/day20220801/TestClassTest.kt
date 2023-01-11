package com.demo.junit.day20220801

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.io.path.Path

internal class TestClassTest {

    @Test
    fun useFileIo() {
        val path = "src/test/resources"
        val file = File(path)
        val absolutePath = file.absolutePath

        println(absolutePath)
        assertTrue(absolutePath.endsWith(path))
    }

    @Test
    fun usePath() {
        val path = "src/test/resources"

        // kotlin version
         val resourceDirectory = Path(path)

        // java version
        // val resourceDirectory = Paths.get("src", "test", "resources")

        val absolutePath = resourceDirectory.toFile().absolutePath

        println(absolutePath)
        assertTrue(absolutePath.endsWith(path))
    }

    @Test
    fun useClassLoader() {
        val resourceName = "example_resource.txt"
        val classLoader = javaClass.classLoader
        val file = File(classLoader.getResource(resourceName).file)
        val absolutePath = file.absolutePath

        println(absolutePath)
        assertTrue(absolutePath.endsWith(resourceName))
    }
}