package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnoreType
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.fasterxml.jackson.annotation.JsonView
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern


class TestClass {
}

class CustomDateDeserializer(t: Class<Date>? = null) : StdDeserializer<Date>(t) {
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

class PhoneNumberFilter {
    private val phoneNumberPattern: Pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}")

    override fun equals(other: Any?): Boolean {

        if (other == null || other !is String) {
            return false
        }
        return !phoneNumberPattern.matcher(other.toString()).matches()
    }

    override fun hashCode(): Int {
        return phoneNumberPattern.hashCode()
    }
}

class CompanyNameFilter {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is String) {
            return false
        }

        return !other.contentEquals("LINE")
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
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


class UnwrappedUser(
    val id: Int,
    @get:JsonUnwrapped
    var name: Name
) {
    override fun toString(): String {
        return "UnwrappedUser(id=$id, name=$name)"
    }
}

class Name(
    val firstName: String,
    val lastName: String
) {
    override fun toString(): String {
        return "Name(firstName='$firstName', lastName='$lastName')"
    }
}

class View {
    class User
    class Admin
}

class User {
    @JsonProperty("user_id")
    @JsonView(View.User::class, View.Admin::class)
    var userId: Long? = null

    @JsonProperty("password")
    @JsonView(View.Admin::class)
    var password: String? = null
}

class Parent {
    var id: Int? = null

    @JsonManagedReference
    var children: MutableList<Child> = mutableListOf()
    override fun toString(): String {
        return "Parent(id=$id, children=$children)"
    }
}

class Child {
    var id: Int? = null

    @JsonBackReference
    var parent: Parent? = null
    override fun toString(): String {
        return "Child(id=$id, parent=$parent)"
    }
}


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
class ItemWithIdentity {
    var id = 0
    var itemName: String? = null
    var owner: UserWithIdentity? = null
    override fun toString(): String {
        return "ItemWithIdentity(id=$id, itemName=$itemName, owner=$owner)"
    }
}

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
class UserWithIdentity {
    fun addItem(item: ItemWithIdentity) {
        userItems.add(item)
    }

    override fun toString(): String {
        return "UserWithIdentity(id=$id, name=$name, userItems=$userItems)"
    }

    var id = 0
    var name: String? = null
    var userItems: MutableList<ItemWithIdentity> = mutableListOf()
}

class Employee(
    val name: String,
    val age: Int,
    val address: Address
)

class Address(
    val address1: String,
    val address2: String
)

@JsonIgnoreType
class MyMixInForIgnoreType {}