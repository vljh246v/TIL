package com.demo.companystudykotlin.item33

class Test {

}
/*

class MyLinkedList<T> (
    val head: T,
    val tail: MyLinkedList<T>?
) {
    companion object {
        fun <T> of(vararg elements: T): MyLinkedList<T>? {
            */
/*...*//*

        }
    }
}

val list = MyLinkedList(1, MyLinkedList(2, null))


fun <T> myLinkedListOf(
    vararg elements: T
): MyLinkedList<T>? {
    if(elements.isEmpty()) {
        return null
    }
    val head = elements.first()
    val elementsTail = elements
        .copyOfRange(1, elements.size)
    val tail = myLinkedListOf(*elementsTail)
    return MyLinkedList(head, tail)
}

val list = myLinkedListOf(1, 2)
*/


interface Car {
    fun drive()
}

class Bus : Car {
    override fun drive() {
        println("Bus!")
    }
}

class Sedan : Car {
    override fun drive() {
        println("Sedan!")
    }
}

inline fun <reified T> createCar(): T? {
    return when (T::class) {
        Bus::class -> Bus() as T
        Sedan::class -> Sedan() as T
        else -> null
    }
}

interface Tool {
    companion object { /*...*/ }
}
/*

fun Tool.Companion.createBigTool( */
/*...*//*
 ): BigTool {
    // ...
}
*/


class People(
    val id: String = ""
)

class NonPeople(
    val id: String = ""
)

inline fun <reified T> MyList(
    size: Int,
    init: (index: Int) -> T
): List<T> {
    if(T::class != People::class) {
        throw IllegalArgumentException("error!")
    }
    return MutableList(size, init)
}

val list1 = MyList(3) { i -> People(i.toString()) }
val list2 = MyList(3) { i -> NonPeople(i.toString()) }

fun main() {
    list1.forEach { println( it.id )}
    list2.forEach { println( it.id )}
}


class Pizzaaaaaa{
    val name: String
    val size: Int
    val cheese: Boolean
    val olives: Boolean

    constructor(name: String, size: Int, cheese: Boolean, olives: Boolean) {
        this.name = name
        this.size = size
        this.cheese = cheese
        this.olives = olives
    }

    constructor(name: String, cheese: Boolean): this(name, size = 1, cheese, false)
    constructor(name: String): this(name, size = 1, true, false)
}

