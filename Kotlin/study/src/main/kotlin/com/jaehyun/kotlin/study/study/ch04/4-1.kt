package com.jaehyun.kotlin.study.study.ch04

interface Clickable {
    fun click()
}

class Button : Clickable {
    override fun click() {
        println("I was clicked")
    }
}

fun main(){
    Button().click()
}