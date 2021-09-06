package com.jaehyun.kotlin.study.study.ch06

fun strLenSafe (s: String?) : Int =
    if(s != null) s.length else 0

fun printAllCaps (s: String?) {
    val allCaps: String? = s?.uppercase()
    println(allCaps)
}

class Employee(val name: String, val manager: Employee?)

fun managerName(employee: Employee): String? = employee.manager?.name

fun main() {
//    val x: String? = null
//    println(strLenSafe(x))
//
//    printAllCaps("abc")
//    printAllCaps(null)

    val ceo = Employee("boss", null)
    val developer = Employee("dev", ceo)

    println(managerName(developer))
    println(managerName(ceo))
}