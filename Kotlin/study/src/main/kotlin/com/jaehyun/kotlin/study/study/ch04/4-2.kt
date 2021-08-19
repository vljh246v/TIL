package com.jaehyun.kotlin.study.study.ch04

// class User(val nickname: String)

class User(val nickname: String, val isSubscribed: Boolean = true)

class Context
class AttributeSet

val MY_STYLE = AttributeSet()

open class View2 {
    constructor(ctx: Context) {
        // 코드
    }

    constructor(ctx: Context, attr: AttributeSet) {
        // 코드
    }
}

class MyButton : View2 {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr)
}

class MyButton2 : View2 {
    constructor(ctx: Context) : this(ctx, MY_STYLE) {
        //  코드
    }

    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr) {
        // 코드
    }
}

fun main() {
    val hyun = User("현석")
    println(hyun.isSubscribed)
}
