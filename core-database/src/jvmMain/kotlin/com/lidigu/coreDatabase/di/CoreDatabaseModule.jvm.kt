package com.lidigu.coreDatabase.di

import com.lidigu.coreDatabase.SqlDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getCoreDatabaseModule(): Module {
    return module {
        single { SqlDriverFactory().getSqlDriver() }
    }
}