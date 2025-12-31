package com.lidigu.gameapp

import androidx.compose.ui.window.ComposeUIViewController
import com.lidigu.gameapp.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin {
        AppDiSetupKt.doInitKoin()
    }

    App() }