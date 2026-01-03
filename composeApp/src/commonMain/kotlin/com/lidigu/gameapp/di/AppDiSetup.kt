package com.lidigu.gameapp.di

import com.lidigu.coreDatabase.di.getCoreDatabaseModule
import com.lidigu.coreNetwork.di.getCoreNetworkModule
import com.lidigu.game.data.di.getGameDataModule
import com.lidigu.game.domain.di.getGameDomainModule
import com.lidigu.game.ui.di.getGameUiModule
import com.lidigu.search.data.di.getSearchDataModule
import com.lidigu.search.domain.di.getSearchDomainModule
import com.lidigu.search.ui.di.getSearchUiModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(koinApplication: ((KoinApplication)-> Unit)? = null){
    startKoin {
        modules(
            getCoreNetworkModule(),
            getGameDataModule(),
            getGameDomainModule(),
            getGameUiModule(),
            getSearchDataModule(),
            getSearchDomainModule(),
            getSearchUiModule(),
            getCoreDatabaseModule()
        )
    }
}