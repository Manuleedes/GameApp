package com.lidigu.coreDatabase.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import com.lidigu.coreDatabase.AppDatabase
import com.lidigu.coreDatabase.SqlDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getCoreDatabaseModule(): Module {
    return module {
        single { SqlDriverFactory(get<Context>()).getSqlDriver() }
        single { AppDatabase.invoke(get<SqlDriver>()) }
    }
}