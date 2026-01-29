package com.lidigu.gameapp

import androidx.compose.ui.window.ComposeUIViewController
import com.lidigu.gameapp.di.initKoin

fun initKoin() {
    com.lidigu.gameapp.di.initKoin()
}

fun MainViewController() = ComposeUIViewController {
    App() 
}