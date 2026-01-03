package com.lidigu.coreDatabase

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class SqlDriverFactory actual constructor(context: Any?) {
    actual fun getSqlDriver(): app.cash.sqldelight.db.SqlDriver {
        return JdbcSqliteDriver(
            "jdbc:sqlite:AppDatabase.db"
        )
    }
}