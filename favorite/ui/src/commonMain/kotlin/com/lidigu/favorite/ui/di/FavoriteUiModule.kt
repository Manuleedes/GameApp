package com.lidigu.favorite.ui.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.lidigu.favorite.ui.FavoriteViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

fun getFavoriteUiModule(): Module{
    return module {
        viewModel {
            FavoriteViewModel(
                getAllLocalCasedGamesUseCase = get(),
                deleteUseCase = get()
            )
        }
    }
}