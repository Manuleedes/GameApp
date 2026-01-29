package com.lidigu.coreDatabase

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class SqlDriverFactory actual constructor(context: Any?) {
    actual fun getSqlDriver(): app.cash.sqldelight.db.SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:AppDatabase.db")
        try {
            AppDatabase.Schema.create(driver)
        } catch (e: Exception) {
            // Assume table already exists
        }
        return driver
    }
}