package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

internal class TestClassTest {

    @Test
    fun jsonAnyGetterTest() {

        class ExtendableBean(val name: String) {
            @get:JsonAnyGetter(enabled = false)
            val properties: MutableMap<String, String> = mutableMapOf()
        }

        val bean = ExtendableBean("My bean")
        bean.properties["attr1"] = "val1"
        bean.properties["attr2"] = "val2"

        val result = ObjectMapper().writeValueAsString(bean)
        print(result)
    }
}

