package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TestClassTest {

    @Test
    fun jsonAnyGetterTest() {

        class ExtendableBean(val name: String) {
            @get:JsonAnyGetter
            val properties: MutableMap<String, String> = mutableMapOf()
        }

        val bean = ExtendableBean("My bean")
        bean.properties["attr1"] = "val1"
        bean.properties["attr2"] = "val2"

        val result = ObjectMapper().writeValueAsString(bean)
        print(result)
        assertThat(result).doesNotContain("properties")
        assertThat(result).contains("attr1")
        assertThat(result).contains("attr2")
    }


    @Test
    fun jsonGetterTest() {

        class MyBean(val name: String) {

            @JsonGetter("name")
            fun getMyName(): String {
                return "My name is $name"
            }
        }

        val bean = MyBean("My bean")

        val result = ObjectMapper().writeValueAsString(bean)
        print(result)
        assertThat(result).contains("My name is")
    }

    @Test
    fun jsonPropertyOrderTest() {

        @JsonPropertyOrder(alphabetic = true)
        class MyBean(val name: String, val age: Int, val country: String)

        val bean = MyBean("My bean", 32, "KR")

        val result = ObjectMapper().writeValueAsString(bean)
        print(result)
        assertThat(result).contains("My bean")
        assertThat(result).contains(32.toString())
        assertThat(result).contains("KR")
    }
}

