package com.lidigu.game.ui.di

import com.lidigu.game.domain.useCases.GetGameDetailsUseCase
import com.lidigu.game.domain.useCases.GetGamesUseCase
import com.lidigu.game.ui.game.GameViewModel
import com.lidigu.game.ui.gameDetails.GameDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getGameUiModule() = module {
    viewModel { GameViewModel(getGamesUseCase = get<GetGamesUseCase>()) }
    viewModel { GameDetailsViewModel(getGameDetailsUseCase = get<GetGameDetailsUseCase>()) }

}