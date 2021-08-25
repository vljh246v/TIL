package com.jaehyun.kotlin.study.study.ch04

object Payroll {
    val allEmployees = arrayListOf<Person>()
    fun calculateSalary() {
        for (person in allEmployees) {
        }
    }

    fun add(person: Person) {
        allEmployees.add(person)
    }
}

class Person

fun main() {
    println("4-4")
    Payroll.calculateSalary()
    Payroll.add(Person())
}
