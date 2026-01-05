package com.lidigu.game.data.repository

import com.lidigu.common.data.mappers.toDomainListOfGames
import com.lidigu.common.domain.model.Game
import com.lidigu.coreDatabase.AppDatabase
import com.lidigu.coreNetwork.apiService.ApiService
import com.lidigu.game.data.mappers.toDomainGameDetails
import com.lidigu.game.domain.model.GameDetails
import com.lidigu.game.domain.repository.GameRepository

class GameRepositoryImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
): GameRepository{
    override suspend fun getGames(): Result<List<Game>> {
        val result = apiService.getGames()
     return   if (result.isSuccess){
          Result.success(result.getOrThrow().results.toDomainListOfGames())
        }else{
            Result.failure(result.exceptionOrNull()!!)
        }

    }
    override suspend fun getDetails(id: Int): Result<GameDetails> {
        val result = apiService.getDetails(id)
      return  if (result.isSuccess){
            Result.success(result.getOrThrow().toDomainGameDetails())
        }else{
            Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun save(id: Int, image: String, name: String) {
        appDatabase.appDatabaseQueries
            .upsert(id.toLong(), image,name)
    }
    override suspend fun delete(id: Int) {
        appDatabase.appDatabaseQueries
            .delete(id.toLong())
    }

}