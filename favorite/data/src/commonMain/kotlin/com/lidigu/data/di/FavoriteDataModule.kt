package com.lidigu.data.di

import com.lidigu.coreDatabase.AppDatabase
import com.lidigu.data.repository.FavoriteRepositoryImpl
import com.lidigu.favorite.domain.repository.FavoriteRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun getFavoriteDataModule(): Module {
    return module {
        factory <FavoriteRepository>{ FavoriteRepositoryImpl(get<AppDatabase>()) }
    }

}