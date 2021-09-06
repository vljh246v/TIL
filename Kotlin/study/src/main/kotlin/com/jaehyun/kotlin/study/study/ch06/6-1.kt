package com.jaehyun.kotlin.study.study.ch06

fun strLenSafe(s: String?): Int = s?.length ?: 0

fun printAllCaps(s: String?) {
    val allCaps: String? = s?.uppercase()
    println(allCaps)
}

class Employee(val name: String, val manager: Employee?)

fun managerName(employee: Employee): String? = employee.manager?.name

class Address(val streetAddress: String, val zipCode: Int, val city: String, val country: String)
class Company(val name: String, val address: Address?)
class Person(val name: String, val company: Company?)

fun Person.countryName(): String = company?.address?.country ?: "Unknown"

fun foo(s: String?) {
    val t: String = s ?: ""
}

fun main() {
//    val x: String? = null
//    println(strLenSafe(x))
//
//    printAllCaps("abc")
//    printAllCaps(null)

//    val ceo = Employee("boss", null)
//    val developer = Employee("dev", ceo)
//
//    println(managerName(developer))
//    println(managerName(ceo))

    val person = Person("Dmitry", null)
    println(person.countryName())

    val x: String? = null
    println(strLenSafe(x))

}