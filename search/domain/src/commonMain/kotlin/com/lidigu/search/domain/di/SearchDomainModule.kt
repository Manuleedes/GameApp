package com.lidigu.search.domain.di

import com.lidigu.search.domain.useCases.SearchGameUseCase
import org.koin.dsl.module

fun getSearchDomainModule() = module {
    factory { SearchGameUseCase(searchRepository = get())}
}