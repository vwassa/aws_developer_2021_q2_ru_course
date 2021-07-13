package com.vwassa.example.app

import com.vwassa.example.db.bindServices
import com.vwassa.example.db.initDB
import com.vwassa.example.routes.registerCustomerRoutes
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.serialization.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.kodein.di.ktor.di

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    initDB()
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
    }
    install(ContentNegotiation) {
        json()
    }
    install(Authentication) {
        basic("myBasicAuth") {
            realm = "Ktor Server"
            validate { if (it.name == "test" && it.password == "password") UserIdPrincipal(it.name) else null }
        }
    }
    install(DataConversion)

    install(CallLogging)

    di {
        bindServices()
    }

    registerCustomerRoutes()
}