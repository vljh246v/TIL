package com.demo.plugins

import com.demo.routes.customerRouting
import com.demo.routes.sampleRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {

    routing {
        sampleRouting()
        customerRouting()
    }
}
