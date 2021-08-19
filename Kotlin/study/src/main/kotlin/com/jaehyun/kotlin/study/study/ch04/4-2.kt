package com.jaehyun.kotlin.study.study.ch04

// class User(val nickname: String)

class User(val nickname: String, val isSubscribed: Boolean = true)

class Context
class AttributeSet

open class View2 {
    constructor(ctx: Context) {
        // 코드
    }

    constructor(ctx: Context, attr: AttributeSet) {
        // 코드
    }
}
fun main() {
    val hyun = User("현석")
    println(hyun.isSubscribed)
}
