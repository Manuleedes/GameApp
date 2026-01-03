package com.lidigu.coreDatabase

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight:sqlite-driver-js

actual class SqlDriverFactory actual constructor(context: Any?) {

    actual suspend fun getSqlDriver(): SqlDriver {
        val driver = JsSqliteDriver(AppDatabase.Schema)
        AppDatabase.Schema.create(driver)
        return driver
    }
}