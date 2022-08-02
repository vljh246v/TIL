package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonRawValue
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
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

    @Test
    fun jsonRawValueTest() {

        class MyBean(val name: String, @JsonRawValue val json: String)

        val bean = MyBean("My bean",  "{\"attr\":false}")

        val result = ObjectMapper().writeValueAsString(bean)
        print(result)
        assertThat(result).contains("{\"attr\":false}")
    }

    @Test
    fun jsonValueTest_class() {

        class MyBean(val name: String, val age: Int, val country: String) {

            @JsonValue
            fun getMyBean(): String {
                return "MyBean(name='$name', age=$age, country='$country')"
            }
        }

        val bean = MyBean("My bean", 32, "KR")

        val result = ObjectMapper().writeValueAsString(bean)
        print(result)
        assertThat(result).contains("My bean")
        assertThat(result).contains(32.toString())
        assertThat(result).contains("KR")
    }

    @Test
    fun jsonValueTest_enum() {
        val result = ObjectMapper().writeValueAsString(Type.TYPE_A)
        print(result)
        assertThat(result).contains("Type A")
    }

    enum class Type(val id: Int, private val typeName: String) {
        TYPE_A(1, "Type A"),
        TYPE_B(1, "Type B");

        @JsonValue
        fun getName(): String {
            return typeName
        }
    }

    @Test
    fun jsonRootNameTest() {

        @JsonRootName(value = "bean")
        class MyBean(val name: String, val age: Int, val country: String)

        val bean = MyBean("My bean", 32, "KR")

        val mapper = ObjectMapper()
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE)
        val result = mapper.writeValueAsString(bean)

        print(result)
        assertThat(result).contains("bean")
        assertThat(result).contains("My bean")
        assertThat(result).contains(32.toString())
        assertThat(result).contains("KR")
    }


}