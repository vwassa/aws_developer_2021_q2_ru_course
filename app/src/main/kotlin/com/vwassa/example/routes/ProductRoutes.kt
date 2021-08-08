package com.vwassa.example.routes

import com.vwassa.example.data.Product
import com.vwassa.example.db.ProductService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.products() {
    val productService by closestDI().instance<ProductService>()

    route("product") {
        put("/{id}/add") {
            val productId = call.parameters["id"] ?: return@put call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val product = call.receive<Product>().copy(id = productId)
            productService.addProduct(product)
            call.respond(HttpStatusCode.Created)
        }
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val result = productService.getProduct(id)
            if (result.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, result[0])
            } else {
                call.respond(HttpStatusCode.NotFound, "Product with id = $id didn't find")
            }
        }
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            productService.deleteProduct(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Routing.healthCheck(){
    route("health"){
        get("check"){
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Routing.apiRoute() {
    authenticate("myBasicAuth") {
        route("/api/v1") {
            products()
        }
    }
}

fun Application.registerCustomerRoutes() {
    routing {
        healthCheck()
        apiRoute()
    }
}