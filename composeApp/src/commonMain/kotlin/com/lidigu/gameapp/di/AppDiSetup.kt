package com.lidigu.gameapp.di

import com.lidigu.coreNetwork.di.getCoreNetworkModule
import com.lidigu.game.data.di.getGameDataModule
import com.lidigu.game.domain.di.getGameDomainModule
import com.lidigu.game.ui.di.getGameUiModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(koinApplication: ((KoinApplication)-> Unit)? = null){
    startKoin {
        modules(
            getCoreNetworkModule(),
            getGameDataModule(),
            getGameDomainModule(),
            getGameUiModule()
        )
    }
}