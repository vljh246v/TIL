package com.demo.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Route.sampleRouting() {
    route("/") {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}