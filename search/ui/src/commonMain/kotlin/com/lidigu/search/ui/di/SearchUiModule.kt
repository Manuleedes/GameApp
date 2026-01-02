package com.lidigu.search.ui.di

import com.lidigu.search.domain.useCases.SearchGameUseCase
import com.lidigu.search.ui.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getSearchUiModule() = module {
    viewModel { SearchViewModel(searchGameUseCase = get<SearchGameUseCase>()) }

}