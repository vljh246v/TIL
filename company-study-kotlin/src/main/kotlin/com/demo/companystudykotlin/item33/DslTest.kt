package com.demo.companystudykotlin.item33

class DslTest {
}

fun plus(a: Int, b: Int) = a + b

val plus1: (Int, Int) -> Int = {a, b -> a + b}
val plus2: (Int, Int) -> Int = fun(a, b) = a + b

fun main() {
}



fun table(init: TableBuilder.() -> Unit): TableBuilder {

}

class TableBuilder {
    fun tr(init: TrBuilder.() -> Unit) {

    }
}

class TrBuilder {
    fun td(init: TdBuilder.() -> Unit) {

    }
}

class TdBuilder {
    var text = ""

    operator fun String.unaryPlus() {
        text += this
    }
}


fun createTable(): TableDsl = table {
    this.tr {
        for (i in 1..2) {
            td {
                +"This is column %i"
            }
        }
    }
}


val person = person {
    name = "Lim"
    age = 33
    address {
        street = "322"
        number = 2501
        city = "평택"
    }
}


fun person(block: Person.() -> Unit): Person {
    val p = Person()
    p.block()
    return p
}

fun Person.address(block: Address.() -> Unit) {
    address = Address().apply(block)
}

data class Person(var name: String? = null,
                  var age: Int? = null,
                  var address: Address? = null)


data class Address(var street: String? = null,
                   var number: Int? = null,
                   var city: String? = null)