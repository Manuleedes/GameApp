package com.lidigu.gameapp

import android.app.Application
import com.lidigu.gameapp.di.initKoin
import org.koin.dsl.module

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            it.modules(
                module {
                    single { this@BaseApplication.applicationContext }
                }
            )
        }
    }

}