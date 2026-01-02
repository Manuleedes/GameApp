package com.lidigu.search.data.repository

import com.lidigu.common.data.mappers.toDomainListOfGames
import com.lidigu.common.domain.model.Game
import com.lidigu.coreNetwork.apiService.ApiService
import com.lidigu.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val apiService: ApiService
): SearchRepository {
    override suspend fun search(q: String): Result<List<Game>> {
     return   try {
            val response = apiService.search(q)
           val data = response.getOrThrow().results.toDomainListOfGames()
            Result.success(data)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}