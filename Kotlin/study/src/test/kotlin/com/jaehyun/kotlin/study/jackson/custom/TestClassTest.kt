package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.annotation.JacksonInject
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonFormat
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
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.InjectableValues
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


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

    @Test
    fun jsonPropertyTest() {

        class MyBean {
            var id: String? = null
            var name: String? = null

            @JsonProperty("name")
            fun setTheName(theName: String) {
                this.name = theName
            }

            @JsonProperty("name")
            fun getTheName(): String? {
                return this.name
            }

            override fun toString(): String {
                return "MyBean(id='$id', name='$name')"
            }
        }

        var bean = MyBean().apply {
            id = "1"
            name = "demo"
        }
        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains("id")
        assertThat(result).contains("1")
        assertThat(result).contains("name")
        assertThat(result).contains("demo")

        bean = ObjectMapper().readValue(result, MyBean::class.java)
        print(result)
        assertThat(bean.id).contains("1")
        assertThat(bean.name).contains("demo")
    }

    @Test
    fun jsonFormatTest() {
        class MyBean(
            var name: String,
            @get:JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "dd-MM-yyyy hh:mm:ss"
            )
            var date: Date
        ) {
            override fun toString(): String {
                return "MyBean(name='$name', date=$date)"
            }
        }

        val df = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        df.timeZone = TimeZone.getTimeZone("UTC")

        val toParse = "15-08-2022 01:00:00"
        val parse = df.parse(toParse)
        val bean = MyBean("party", parse)
        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains(toParse)
    }

    @Test
    fun jsonUnwrappedTest() {

        val bean = UnwrappedUser(1, Name("demo", "lim"))
        val result = ObjectMapper().writeValueAsString(bean)

        print(result)
        assertThat(result).contains("demo")
        assertThat(result).contains("1")
        assertThat(result).doesNotContain("name")
    }

    @Test
    fun jsonViewTest() {

        val bean = User()
            .apply {
                this.userId = 10L
                this.password = "1234"
            }

        var result = ObjectMapper()
            .writerWithView(View.User::class.java)
            .writeValueAsString(bean)

        print(result)
        assertThat(result).contains("user_id")
        assertThat(result).doesNotContain("password")

        result = ObjectMapper()
            .writerWithView(View.Admin::class.java)
            .writeValueAsString(bean)

        print(result)
        assertThat(result).contains("user_id")
        assertThat(result).contains("password")
    }


    @Test
    fun jsonReferenceTest() {
        val parent = Parent().apply {
            this.id = 1

            val child1 = Child().apply {
                this.id = 1
            }

            val child2 = Child().apply {
                this.id = 2
            }

            this.children.add(child1)
            this.children.add(child2)

            child1.parent = this
            child2.parent = this
        }

        val result = ObjectMapper()
            .writeValueAsString(parent)

        print(result)
        assertThat(result).contains("children")
    }

    @Test
    @Throws(JsonProcessingException::class)
    fun jsonIdentityInfoTest() {
        val user = UserWithIdentity().apply {
            id = 1
            name = "demo"
        }
        val item = ItemWithIdentity().apply {
            id = 2
            itemName = "book"
            owner = user
        }
        user.addItem(item)


        val result = ObjectMapper().writeValueAsString(user)
        print(result)
        assertThat(result).contains("userItems")
        assertThat(result).contains("owner")
    }

    @Test
    fun jsonFilterTest() {
        @JsonFilter("myFilter")
        class BeanWithFilter(
            var id: Int,
            var name: String?
        ) {
            override fun toString(): String {
                return "BeanWithFilter(id=$id, name=$name)"
            }
        }

        val bean = BeanWithFilter(1, "demo")

        val filters = SimpleFilterProvider().addFilter(
            "myFilter",
            SimpleBeanPropertyFilter.filterOutAllExcept("name")
        )

        val result = ObjectMapper()
            .writer(filters)
            .writeValueAsString(bean)

        print(result)
        assertThat(result).contains("name")
        assertThat(result).doesNotContain("id")
    }

    @Test
    fun jacksonAnnotationsInsideTest() {

        @CustomAnnotation
        class BeanWithCustomAnnotation(
            var id: Int,
            var name: String?,
            var dateCreated: Date?
        ) {
            override fun toString(): String {
                return "BeanWithCustomAnnotation(id=$id, name=$name, dateCreated=$dateCreated)"
            }
        }

        val bean = BeanWithCustomAnnotation(1, "demo", null)


        val result = ObjectMapper()
            .writeValueAsString(bean)

        print(result)
        assertThat(result).doesNotContain("dateCreated")
        assertThat(result).contains("id")
        assertThat(result).contains("name")
    }

    @Test
    fun mixInTest() {

        val bean = Employee("demo", 32, Address("add1", "add2"))

        var result = ObjectMapper()
            .writeValueAsString(bean)

        print(result)
        assertThat(result).contains("name")
        assertThat(result).contains("age")
        assertThat(result).contains("address")

        val mapper = ObjectMapper()
        mapper.addMixIn(Address::class.java, MyMixInForIgnoreType::class.java)

        result = mapper.writeValueAsString(bean)
        assertThat(result).contains("name")
        assertThat(result).contains("age")
        assertThat(result).doesNotContain("address")
    }
}