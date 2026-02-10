package com.lidigu.game.data.di

import com.lidigu.game.data.repository.DownloadRepositoryImpl
import com.lidigu.game.data.repository.GameRepositoryImpl
import com.lidigu.game.domain.repository.DownloadRepository
import com.lidigu.game.domain.repository.GameRepository
import org.koin.dsl.module

fun getGameDataModule() = module {
    factory <GameRepository>{
        GameRepositoryImpl(apiService = get(), appDatabase = get())
    }
    factory <DownloadRepository>{
        DownloadRepositoryImpl(httpClient = get())
    }
}