package com.vwassa.example.db

import com.vwassa.example.data.Product
import com.vwassa.example.data.Products
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

class ProductService {
    fun getProduct(id: String): List<Product> = transaction {
        val result = Products.select { Products.id eq id }
        if (result.having == null) {
            emptyList()
        } else {
            val raw = result.single()
            listOf(Product(id = raw[Products.id], name = raw[Products.name], price = raw[Products.price]))
        }
    }

    fun addProduct(product: Product) = transaction {
        val hasProduct = Products.select { Products.id eq product.id }.having != null
        if (hasProduct) {
            Products.update {
                it[id] = product.id
                it[name] = product.name
                it[price] = product.price
            }
        } else {
            Products.insert {
                it[id] = product.id
                it[name] = product.name
                it[price] = product.price
            }
        }
    }

    fun deleteProduct(id: String) = transaction {
        Products.deleteWhere { Products.id eq id }
    }
}

fun DI.MainBuilder.bindServices(){
    bind<ProductService>() with singleton { ProductService() }
}