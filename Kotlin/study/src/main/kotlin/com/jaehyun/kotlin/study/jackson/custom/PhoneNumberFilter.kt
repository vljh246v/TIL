package com.jaehyun.kotlin.study.jackson.custom

import java.util.regex.Pattern

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