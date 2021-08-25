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

interface User2 {
    val nickName: String
}

class PrivateUser(override val nickName: String) : User2

class SubscribingUser(private val email: String) : User2 {
    override val nickName: String
        get() = email.substringBefore('@')
}

class FacebookUser(private val accountId: Int) : User2 {
    override val nickName: String = getFacebookName(accountId)
    private fun getFacebookName(accountId: Int): String = accountId.toString()
}

interface User3 {
    val email: String
    val nickName: String
        get() = email.substringBefore('@')
}

class CustomUser(override val email: String) : User3

class User4(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            print(value)
            field = value
        }
}

class LengthCounter {
    var counter: Int = 0
        private set
    fun addWord(word: String) {
        counter += word.length
    }
}

fun main() {
    val hyun = User("현석")
    println(hyun.isSubscribed)
}
