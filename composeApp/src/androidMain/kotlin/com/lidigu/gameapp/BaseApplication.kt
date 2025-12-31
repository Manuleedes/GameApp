package com.lidigu.gameapp

import android.app.Application
import com.lidigu.gameapp.di.initKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

}