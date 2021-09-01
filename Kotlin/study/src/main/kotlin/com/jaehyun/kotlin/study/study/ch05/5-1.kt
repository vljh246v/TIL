package com.jaehyun.kotlin.study.study.ch05

data class Person(val name: String, val age: Int)


fun findTheOldset(people: List<Person>) {
    var maxAge = 0
    var theOldset: Person? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldset = person
        }
    }

    println(theOldset)
}


fun main() {
    // 컬렉션 직접 검색
    findTheOldset(listOf(Person("A", 29), Person("B", 31)))

    // 람다를 사용
    println(listOf(Person("A", 29), Person("B", 31)).maxByOrNull { it.age })

    val sum = { x: Int, y: Int -> x + y }
    println(sum(1, 2))

    run { println(12) }
}