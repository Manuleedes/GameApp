package com.lidigu.gameapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform