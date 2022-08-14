package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.annotation.JacksonInject
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonIgnoreType
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonRawValue
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.InjectableValues
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.Date


class TestClassTest {

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

        val bean = MyBean("My bean", "{\"attr\":false}")

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
            @get:JsonSerialize(using = CustomDateSerializer::class) val date: Date
        )

        val df = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val toParse = "03-08-2022 12:18:00"
        val date = df.parse(toParse)

        val bean = MyBean("My bean", date)

        val mapper = ObjectMapper()
        val result = mapper.writeValueAsString(bean)

        print(result)
        assertThat(result).contains(toParse)
    }

    @Test
    fun jsonCreatorTest() {
        class BeanWithCreator @JsonCreator constructor(
            @JsonProperty("id") val id: Int,
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
        class BeanWithInject {

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
            fun setTheName(theName: String) {
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

    @Test
    fun jsonIgnorePropertiesTest() {
        @JsonIgnoreProperties(value = ["firstName"])
        class BeanWithIgnore(
            var firstName: String? = null,
            var lastName: String? = null
        ) {
            override fun toString(): String {
                return "AliasBean(firstName=$firstName, lastName=$lastName)"
            }
        }

        val bean = BeanWithIgnore("jaehyun", "lim")

        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains("lim")
        assertThat(result).doesNotContain("jaehyun")
    }

    @Test
    fun jsonIgnoreTest() {
        class BeanWithIgnore(
            @JsonIgnore
            var firstName: String? = null,
            var lastName: String? = null
        ) {
            override fun toString(): String {
                return "AliasBean(firstName=$firstName, lastName=$lastName)"
            }
        }

        val bean = BeanWithIgnore("jaehyun", "lim")

        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains("lim")
        assertThat(result).doesNotContain("jaehyun")
    }

    @Test
    fun jsonIgnoreTypeTest() {

        @JsonIgnoreType
        class Name(
            var firstName: String? = null,
            var lastName: String? = null
        ) {
            override fun toString(): String {
                return "Name(firstName=$firstName, lastName=$lastName)"
            }

        }

        class User(
            var id: String,
            var name: Name
        ) {
            override fun toString(): String {
                return "User(id='$id', name=$name)"
            }
        }

        val bean = User("1", Name("jaehyun", "lim"))

        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains("1")
        assertThat(result).doesNotContain("jaehyun")
        assertThat(result).doesNotContain("lim")
    }

    @Test
    fun jsonIncludeTest_NON_NULL() {
        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        class MyBean(
            var firstName: String? = null,
            var lastName: String? = null
        ) {
            override fun toString(): String {
                return "MyBean(firstName=$firstName, lastName=$lastName)"
            }
        }

        val bean = MyBean("jaehyun", null)

        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains("jaehyun")
        assertThat(result).doesNotContain("lim")
    }

    @Test
    fun jsonIncludeTest_CUSTOM() {
        @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
        class MyBean(
            var firstName: String? = null,
            var lastName: String? = null,
            @get:JsonInclude(content = JsonInclude.Include.CUSTOM, contentFilter = PhoneNumberFilter::class)
            var privateInfo: Map<String, String> = mutableMapOf(),
            @get:JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = CompanyNameFilter::class)
            var companyName: String? = null,
        ) {
            override fun toString(): String {
                return "MyBean(firstName=$firstName, lastName=$lastName, privateInfo=$privateInfo, companyName=$companyName)"
            }
        }

        val bean = MyBean(
            "jaehyun",
            "",
            mapOf("phoneNumber" to "010-1111-2222", "address" to "seoul"),
            "NAVER"
        )

        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains("firstName")
        assertThat(result).contains("phoneNumber")
        assertThat(result).doesNotContain("lastName")
        assertThat(result).doesNotContain("companyName")
    }

    @Test
    fun jsonAutoDetectTest() {

        @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
        class MyBean(
            private var firstName: String? = null,
            var lastName: String? = null
        ) {
            fun getFullName(): String {
                return "$firstName $lastName"
            }

            override fun toString(): String {
                return "MyBean(firstName=$firstName, lastName=$lastName)"
            }
        }

        val bean = MyBean("jaehyun", "lim")

        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains("firstName")
        assertThat(result).contains("lastName")
    }

    @Test
    fun jsonPolymorphicTest_Serialization() {

        val dog = Dog().apply {
            name = "dog"
            barkVolume = 10L
        }
        val cat = Cat().apply {
            name = "cat"
            isNeutering = true
        }

        var result = ObjectMapper().writeValueAsString(dog)
        print(result)
        assertThat(result).contains("type")
        assertThat(result).contains("dog")

        result = ObjectMapper().writeValueAsString(cat)
        print(result)
        assertThat(result).contains("type")
        assertThat(result).contains("cat")
    }

    @Test
    fun jsonPolymorphicTest_Deserialization() {

        val json = "{\"name\":\"lacy\",\"type\":\"dog\",\"barkVolume\":10}"
        val result = ObjectMapper().readValue(json, Animal::class.java)

        print(result)
        assertThat(result).isInstanceOf(Dog::class.java)
    }

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
    )
    @JsonSubTypes(
        JsonSubTypes.Type(Dog::class, name = "dog"),
        JsonSubTypes.Type(Cat::class, name = "cat")
    )
    open class Animal {
        var name: String? = null
    }

    @JsonTypeName("dog")
    class Dog : Animal() {
        var barkVolume = 0L
        override fun toString(): String {
            return "Dog(name='$name', barkVolume=$barkVolume)"
        }
    }

    @JsonTypeName("cat")
    class Cat : Animal() {
        var isNeutering = true
        override fun toString(): String {
            return "Cat(name='$name', isNeutering=$isNeutering)"
        }
    }
}