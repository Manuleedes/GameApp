package com.lidigu.coreDatabase

import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class SqlDriverFactory actual constructor(context: Any?) {
    actual fun getSqlDriver(): app.cash.sqldelight.db.SqlDriver {
        return NativeSqliteDriver(
             AppDatabase.Schema,
            name = "AppDatabase.db"
        )
    }
}