package com.lidigu.search.data.di

import com.lidigu.search.data.repository.SearchRepositoryImpl
import com.lidigu.search.domain.repository.SearchRepository
import org.koin.dsl.module

fun getSearchDataModule() = module{
factory<SearchRepository> { SearchRepositoryImpl(apiService =get())}

}