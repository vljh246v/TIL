package com.design.urlshorten.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : JpaRepository<UrlEntity, Long> {

    fun findByShortUrl(shortUrl: String): UrlEntity?
    fun findByLongUrl(longUrl: String): UrlEntity?
}
