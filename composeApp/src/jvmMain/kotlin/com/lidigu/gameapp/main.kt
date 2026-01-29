package com.lidigu.gameapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lidigu.gameapp.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "GameApp",
        ) {
            App()
        }
    }
}

