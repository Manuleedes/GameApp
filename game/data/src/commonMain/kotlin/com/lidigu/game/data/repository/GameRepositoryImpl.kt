package com.lidigu.game.data.repository

import com.lidigu.common.data.mappers.toDomainListOfGames
import com.lidigu.common.domain.model.Game
import com.lidigu.coreNetwork.apiService.ApiService
import com.lidigu.game.domain.repository.GameRepository

class GameRepositoryImpl(
    private val apiService: ApiService
): GameRepository{
    override suspend fun getGames(): Result<List<Game>> {
        val result = apiService.getGames()
     return   if (result.isSuccess){
          Result.success(result.getOrThrow().results.toDomainListOfGames())
        }else{
            Result.failure(result.exceptionOrNull()!!)
        }

    }

}