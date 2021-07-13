package com.vwassa.example.db

import com.vwassa.example.data.Products
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

const val HIKARI_CONFIG_KEY = "ktor.hikariconfig"

fun Application.initDB() {
    val configPath = environment.config.property(HIKARI_CONFIG_KEY).getString()
    val dbConfig = HikariConfig(configPath)
    val dataSource = HikariDataSource(dbConfig)
    Database.connect(dataSource)
    createTables()
    LoggerFactory.getLogger(Application::class.simpleName).info("Initialized Database")
}

private fun createTables() = transaction {
    addLogger(org.jetbrains.exposed.sql.StdOutSqlLogger)
    exec("SET DB_CLOSE_DELAY -1")
    SchemaUtils.create(Products)

    Products.insert {
        it[id] = "1"
        it[name] = "pan"
        it[price] = 3.0
    }
    Products.insert {
        it[id] = "2"
        it[name] = "notebook"
        it[price] = 5.0
    }
}
