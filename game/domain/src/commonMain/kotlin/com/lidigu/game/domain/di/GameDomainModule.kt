package com.lidigu.game.domain.di

import com.lidigu.game.domain.useCases.DeleteUseCase
import com.lidigu.game.domain.useCases.GetGameDetailsUseCase
import com.lidigu.game.domain.useCases.GetGamesUseCase
import com.lidigu.game.domain.useCases.GetLocalGameUseCase
import com.lidigu.game.domain.useCases.DownloadGameUseCase
import com.lidigu.game.domain.useCases.ReviewGameUseCase
import com.lidigu.game.domain.useCases.SaveGameUseCase
import com.lidigu.game.domain.useCases.UpdateFavoriteUseCase
import org.koin.dsl.module

fun getGameDomainModule() = module {
    factory { GetGamesUseCase(gameRepository = get()) }
    factory { GetGameDetailsUseCase(gameRepository = get()) }
    factory { SaveGameUseCase(gameRepository = get()) }
    factory { GetLocalGameUseCase(gameRepository = get()) }
    factory { DownloadGameUseCase(downloadRepository = get(), gameRepository = get()) }
    factory { ReviewGameUseCase(gameRepository = get()) }
    factory { UpdateFavoriteUseCase(gameRepository = get()) }
    factory { DeleteUseCase(gameRepository = get()) }
}