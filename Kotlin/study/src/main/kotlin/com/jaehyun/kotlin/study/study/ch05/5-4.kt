package com.jaehyun.kotlin.study.study.ch05


fun createAllDoneRunnable(): Runnable {
    return Runnable { println("All done!") }
}

fun main(){
    createAllDoneRunnable().run()
}