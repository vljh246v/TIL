package com.design.urlshorten.repository

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "url_mapping")
class UrlEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    @Column(length = 7)
    open var shortUrl: String? = null,

    @Column(columnDefinition = "TEXT")
    open var longUrl: String? = null
)
