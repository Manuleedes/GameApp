package com.lidigu.game.domain.di

import com.lidigu.game.domain.useCases.DeleteUseCase
import com.lidigu.game.domain.useCases.GetGameDetailsUseCase
import com.lidigu.game.domain.useCases.GetGamesUseCase
import com.lidigu.game.domain.useCases.SaveGameUseCase
import org.koin.dsl.module

fun getGameDomainModule() = module {
    factory { GetGamesUseCase(gameRepository = get()) }
    factory { GetGameDetailsUseCase(gameRepository = get()) }
    factory { SaveGameUseCase(gameRepository = get()) }
    factory { DeleteUseCase(gameRepository = get()) }
}