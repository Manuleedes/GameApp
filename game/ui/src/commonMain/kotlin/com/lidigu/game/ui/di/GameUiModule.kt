package com.lidigu.game.ui.di

import com.lidigu.game.ui.game.GameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getGameUiModule() = module {
    viewModel {GameViewModel(getGamesUseCase = get()) }
}