package com.vwassa.example.data

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import java.util.*

@Serializable
data class Product(val  id: String, val name: String, val price: Double) {
}

object Products : Table() {
    private val _id = integer("_id").autoIncrement()
    val id = varchar("id", 10)
    val name = varchar("name", 255)
    val price = double("price")

    override val primaryKey = PrimaryKey(_id, name = "PK_Product_UID")
}