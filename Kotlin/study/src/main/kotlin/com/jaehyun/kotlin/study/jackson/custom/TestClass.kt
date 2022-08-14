package com.jaehyun.kotlin.study.jackson.custom

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import java.util.regex.Pattern

class TestClass {
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