package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


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

    @Test
    fun jsonSerializeTest() {

        class MyBean(
            val name: String,
            @get:JsonSerialize(using = CustomDateSerializer::class) val date: Date)

        val df = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val toParse = "03-08-2022 12:18:00"
        val date  = df.parse(toParse)

        val bean = MyBean("My bean", date)

        val mapper = ObjectMapper()
        val result = mapper.writeValueAsString(bean)

        print(result)
        assertThat(result).contains(toParse)
    }

    @Test
    fun jsonCreatorTest() {
        class BeanWithCreator @JsonCreator constructor(
            @JsonProperty("id")  val id: Int,
            @JsonProperty("theName") val name: String
        ) {
            override fun toString(): String {
                return "BeanWithCreator(id=$id, name='$name')"
            }
        }

        val json = "{\"id\":1,\"theName\":\"My bean\"}"

        val mapper = ObjectMapper()
        val result = mapper.readerFor(BeanWithCreator::class.java)
            .readValue<BeanWithCreator>(json)

        print(result)
        assertThat(result.name).isEqualTo("My bean")
    }

    @Test
    fun jacksonInjectTest() {
        class BeanWithInject () {

            @JacksonInject("id1")
            var id1: Int? = null

            @JacksonInject("id2")
            var id2: Long? = null

            var name: String? = null
            override fun toString(): String {
                return "BeanWithInject(id1=$id1, id2=$id2, name=$name)"
            }
        }

        val json = "{\"name\":\"My bean\"}"

        val mapper = ObjectMapper()
        val inject = InjectableValues.Std()
            .addValue("id1", 1)
            .addValue("id2", 2L)

        val result = mapper.reader(inject)
            .forType(BeanWithInject::class.java)
            .readValue<BeanWithInject>(json)

        print(result)
        assertThat(result.name).isEqualTo("My bean")
        assertThat(result.id1).isEqualTo(1)
        assertThat(result.id2).isEqualTo(2L)
    }

    @Test
    fun jsonAnySetterTest() {
        class ExtendableBean {
            var name: String? = null
            var properties: MutableMap<String, String> = mutableMapOf()

            @JsonAnySetter
            fun add(key: String, value: String) {
                properties[key] = value
            }

            override fun toString(): String {
                return "ExtendableBean(name=$name, properties=$properties)"
            }
        }

        val json = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}"

        val mapper = ObjectMapper()
        val result = mapper.readerFor(ExtendableBean::class.java)
            .readValue<ExtendableBean>(json)

        print(result)
        assertThat(result.name).isEqualTo("My bean")
        assertThat(result.properties).containsEntry("attr2", "val2")
        assertThat(result.properties).containsEntry("attr1", "val1")
    }

    @Test
    fun jsonSetterTest() {
        class MyBean {
            var myName: String? = null
            val id: Int? = null

            @JsonSetter("name")
            fun setTheName(theName: String){
                this.myName = theName
            }

            override fun toString(): String {
                return "MyBean(myName=$myName, id=$id)"
            }
        }

        val json = "{\"id\":1,\"name\":\"My bean\"}"

        val mapper = ObjectMapper()
        val result = mapper.readerFor(MyBean::class.java)
            .readValue<MyBean>(json)

        print(result)
        assertThat(result.myName).isEqualTo("My bean")
        assertThat(result.id).isEqualTo(1)
    }

    @Test
    fun jsonDeserializeTest() {
        class EventWithSerializer {
            var name: String? = null

            @JsonDeserialize(using = CustomDateDeserializer::class)
            var eventDate: Date? = Date()
        }

        val df = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val json = "{\"name\":\"demo\",\"eventDate\":\"20-12-2014 02:30:00\"}"

        val mapper = ObjectMapper()
        val result = mapper.readerFor(EventWithSerializer::class.java)
            .readValue<EventWithSerializer>(json)

        print(result)
        assertThat(result.name).isEqualTo("demo")
        assertThat(df.format(result.eventDate)).isEqualTo("20-12-2014 02:30:00")
    }

    @Test
    fun jsonAliasTest() {
        class AliasBean {
            @JsonAlias(value = ["fName", "f_name"])
            var firstName: String? = null
            var lastName: String? = null

            override fun toString(): String {
                return "AliasBean(firstName=$firstName, lastName=$lastName)"
            }
        }

        val json = "{\"fName\": \"version\", \"lastName\": \"demo\"}"

        val mapper = ObjectMapper()
        val result = mapper.readerFor(AliasBean::class.java)
            .readValue<AliasBean>(json)

        print(result)
        assertThat(result.firstName).isEqualTo("version")
    }
}

class CustomDateDeserializer(t: Class<Date>? = null): StdDeserializer<Date>(t) {
    companion object {
        private val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
    }

    @Throws(IOException::class, ParseException::class)
    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): Date {
        val date = jsonParser.text

        return formatter.parse(date)
    }
}

class CustomDateSerializer(t: Class<Date>? = null) : StdSerializer<Date>(t) {
    companion object {
        private val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
    }
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(
        value: Date,
        generator: JsonGenerator,
        provider: SerializerProvider
    ) {
        generator.writeString(formatter.format(value))
    }
}