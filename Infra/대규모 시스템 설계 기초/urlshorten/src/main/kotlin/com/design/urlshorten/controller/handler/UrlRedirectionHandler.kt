package com.design.urlshorten.controller.handler

import com.design.urlshorten.service.UrlService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/url")
class UrlRedirectionHandler(
    private val urlService: UrlService
) {

    @GetMapping("/{path}")
    fun redirectUrl(@PathVariable path: String, response: HttpServletResponse) {
        urlService.getLongUrl(path)
            ?.let { longUrl ->
                response.status = HttpStatus.MOVED_PERMANENTLY.value()
                response.setHeader("Location", longUrl)
            } ?: run { response.status = HttpStatus.NOT_FOUND.value() }
    }
}
