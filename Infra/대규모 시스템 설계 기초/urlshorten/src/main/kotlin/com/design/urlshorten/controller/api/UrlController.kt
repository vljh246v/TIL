package com.design.urlshorten.controller.api

import com.design.urlshorten.service.UrlService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/url")
class UrlController(
    private val urlService: UrlService
) {

    @GetMapping("/{shortenUrl}")
    fun getLongUrl(@PathVariable shortenUrl: String): String? {
        return urlService.getLongUrl(shortenUrl)
    }

    @PostMapping
    fun createShortenUrl(@RequestBody request: V1ShortUrlCreateRequest): String {
        return urlService.createShortenUrl(request.longUrl)
    }
}

data class V1ShortUrlCreateRequest(
    val longUrl: String
)
