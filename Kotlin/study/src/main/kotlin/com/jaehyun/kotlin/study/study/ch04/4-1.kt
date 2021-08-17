package com.jaehyun.kotlin.study.study.ch04

import java.io.Serializable

interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable!")
}

interface Focusable {
    fun setFocus(b: Boolean) =
        println("I ${if (b) "got" else "lost"} focus.")

    fun showOff() = println("I'm focusable!!")
}
//
//class Button : Clickable, Focusable {
//    override fun click() {
//        println("I was clicked")
//    }
//
//    override fun showOff() {
//        super<Clickable>.showOff()
//        super<Focusable>.showOff()
//    }
//}

open class RichButton : Clickable { //  열려있는 class
    fun disable() {} // 하위 클래스에서 오버라이드 불가
    open fun animate() {} // 하위 클래스에서 오버라이드 가능
    override fun click() { // 오버라이드한 메소드는 기본적으로 open
        println("Not yet implemented")
    }
}

open class RichButton2 : Clickable {
    final override fun click() { // final 을 붙여 오버라이드 불가로 변경
        println("Not yet implemented")
    }

}

abstract class Animated {
    abstract fun animate()
    open fun stopAnimating() {} // 비추상 함수는 기본적으로 final
    fun animateTwice() {}

}

//internal open class TalkativeButton : Focusable {
//    private fun yell() = println("Hey!")
//    protected fun whisper() = println("Let's talk!")
//}
//
//fun TalkativeButton.giveSpeech() { // public 멤버가 internal 수신 타입인 TalkativeButton 노출
//    yell() // yell은 TalkativeButton의 private 멤버
//    whisper() // whisper 은 TalkativeButton의 protected 멤버
//}


interface State : Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}

fun main() {
}