package com.demo.system.design.ch04.filter

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.spec.internal.HttpStatus
import org.springframework.cloud.contract.wiremock.WireMockSpring
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthFilterTest {

    companion object {
        private val wireMockServer = WireMockServer(WireMockSpring.options().port(8080))

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            wireMockServer.start()
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            wireMockServer.shutdown()
        }
    }

    lateinit var client: WebTestClient

    @BeforeEach
    fun beforeEach() {
        client = WebTestClient.bindToServer().baseUrl("http://localhost:9000").build()
    }

    @AfterEach
    fun afterEach() {
        wireMockServer.resetAll()
    }

    @Test
    fun `not contain auth token`() {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/hello"))
                .willReturn(
                    WireMock.aResponse()
                        .withBody("world")
                        .withStatus(HttpStatus.OK)
                )
        )

        client.get()
            .uri("/hello")
            .exchange()
            .expectStatus()
            .isForbidden
    }

    @Test
    fun `contain auth token`() {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlEqualTo("/hello"))
                .willReturn(
                    WireMock.aResponse()
                        .withBody("world")
                        .withStatus(HttpStatus.OK)
                )
        )

        client.get()
            .uri("/hello")
            .header("X-AUTO-TOKEN", "passToken")
            .exchange()
            .expectStatus()
            .isOk
    }
}
