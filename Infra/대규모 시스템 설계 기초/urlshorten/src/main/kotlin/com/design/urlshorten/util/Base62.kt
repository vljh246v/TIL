package com.design.urlshorten.util

import kotlin.math.floor

class Base62 {

    companion object {
        private val codes = arrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        )

        fun encoding(number: Long): String {
            var quotient = number
            val reminder = mutableListOf<Long>()

            while (quotient >= 62) {
                reminder.add(quotient % 62)
                quotient = floor((quotient / 62).toDouble()).toLong()
            }

            var result = codes[quotient.toInt()].toString()

            while (reminder.size > 0) {
                val last = reminder.removeLast()
                result += codes[last.toInt()].toString()
            }

            return result
        }
    }
}
