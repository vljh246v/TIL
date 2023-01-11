package com.design.urlshorten.domain

import com.design.urlshorten.util.Base62
import kotlin.math.log10

class Url(
    val longURL: String,
    val id: Long? = null
) {

    fun createShortURL(): String {
        if (!validateId()) {
            throw IllegalStateException("짧은 url을 생성할 수 없습니다.")
        }

        return Base62.encoding(id!!)
    }

    private fun hasId(): Boolean = id != null
    private fun validateIdLength() = log10(id?.toDouble() ?: 0.0).toInt() + 1 == 13
    private fun validateId(): Boolean = hasId() && validateIdLength()
}
