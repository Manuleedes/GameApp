package com.lidigu.coreNetwork.di

import com.lidigu.coreNetwork.apiService.ApiService
import com.lidigu.coreNetwork.client.KtorClient
import org.koin.dsl.module

fun getCoreNetworkModule() = module {
    single { KtorClient.getInstance() }
    single {
        ApiService(httpClient = get())
    }
}