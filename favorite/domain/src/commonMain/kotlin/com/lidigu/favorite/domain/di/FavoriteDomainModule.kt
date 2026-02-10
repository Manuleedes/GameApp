package com.lidigu.favorite.domain.di

import com.lidigu.favorite.domain.useCases.DeleteUseCase
import com.lidigu.favorite.domain.useCases.GetAllLocalCasedGamesUseCase
import com.lidigu.favorite.domain.useCases.UpsertUseCase
import com.lidigu.favorite.domain.useCases.GetLocalGameUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

fun getFavoriteDomainModule(): Module {
    return module {
        factory { DeleteUseCase(get()) }
        factory { GetAllLocalCasedGamesUseCase(get()) }
        factory { UpsertUseCase(get()) }
        factory { GetLocalGameUseCase(get()) }
    }
}