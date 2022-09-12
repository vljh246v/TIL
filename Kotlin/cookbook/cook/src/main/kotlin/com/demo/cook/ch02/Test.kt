package com.demo.cook.ch02

import java.text.NumberFormat
import kotlin.math.pow

class Test {
}



class Person(
    val first: String,
    val middle: String?,
    val last: String
)



@JvmOverloads
fun addProduct(name: String, price: Double = 0.0, desc: String? = null)  = "Adding product with $name, ${desc ?: "None"}, and " + NumberFormat.getCurrencyInstance().format(price)

infix fun Int.`**`(x: Int) = toDouble().pow(x).toInt()
infix fun Long.`**`(x: Int) = toDouble().pow(x).toLong()
infix fun Float.`**`(x: Int) = pow(x)
infix fun Double.`**`(x: Int) = pow(x)

fun Int.pow(x: Int) = `**`(x)
fun Long.pow(x: Int) = `**`(x)