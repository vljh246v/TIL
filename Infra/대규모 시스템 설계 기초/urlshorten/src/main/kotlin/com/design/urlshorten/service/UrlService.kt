package com.design.urlshorten.service

import com.design.urlshorten.domain.Url
import com.design.urlshorten.repository.UrlEntity
import com.design.urlshorten.repository.UrlRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UrlService(
    private val urlRepository: UrlRepository
) {

    val shortUrlHost: String = "http://localhost:8080/url"

    fun getLongUrl(shortUrl: String): String? {
        return urlRepository.findByShortUrl(shortUrl)?.longUrl
    }

    @Transactional
    fun createShortenUrl(longUrl: String): String {
        return urlRepository.findByLongUrl(longUrl)?.longUrl
            ?: run {
                val urlEntity = urlRepository.save(UrlEntity(longUrl = longUrl))
                val url = Url(longURL = longUrl, id = urlEntity.id)
                val shortUrl = url.createShortURL()
                urlEntity.shortUrl = shortUrl
                "$shortUrlHost/$shortUrl"
            }
    }
}
