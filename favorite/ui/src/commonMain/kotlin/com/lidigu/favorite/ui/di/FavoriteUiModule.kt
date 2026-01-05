package com.lidigu.favorite.ui.di

import com.lidigu.favorite.ui.FavoriteViewModel
import org.koin.dsl.module

fun getFavoriteUiModule() = module {
    factory {
        FavoriteViewModel(
            getAllLocalCasedGamesUseCase = get(),
            deleteUseCase = get()
        )
    }
}