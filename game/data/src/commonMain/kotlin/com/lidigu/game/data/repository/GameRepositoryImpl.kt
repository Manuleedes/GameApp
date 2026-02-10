package com.lidigu.game.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.lidigu.common.data.mappers.toDomainListOfGames
import com.lidigu.common.domain.model.Game
import com.lidigu.coreDatabase.AppDatabase
import com.lidigu.coreNetwork.apiService.ApiService
import com.lidigu.game.data.mappers.toDomainGameDetails
import com.lidigu.game.domain.model.GameDetails
import com.lidigu.game.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepositoryImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
): GameRepository{
    override suspend fun getGames(): Result<List<Game>> {
        val result = apiService.getGames()
     return   if (result.isSuccess){
          Result.success(result.getOrThrow().games.toDomainListOfGames())
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

    override fun getLocalGame(id: Int): Flow<Game?> {
        return appDatabase.appDatabaseQueries
            .getGameById(id.toLong())
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { entity ->
                entity?.let {
                    Game(
                        id = it.id.toInt(),
                        name = it.name,
                        imageBackground = it.image,
                        isFavorite = it.isFavorite == 1L,
                        isDownloaded = it.isDownloaded == 1L,
                        rating = it.rating?.toInt(),
                        review = it.review
                    )
                }
            }
    }

    override suspend fun saveGame(id: Int, image: String, name: String, isFavorite: Boolean, isDownloaded: Boolean) {
        appDatabase.appDatabaseQueries
            .insertGame(
                id = id.toLong(),
                image = image,
                name = name,
                isFavorite = if (isFavorite) 1L else 0L,
                isDownloaded = if (isDownloaded) 1L else 0L,
                rating = null,
                review = null
            )
    }

    override suspend fun updateFavorite(id: Int, isFavorite: Boolean) {
        appDatabase.appDatabaseQueries.updateFavorite(
            isFavorite = if (isFavorite) 1L else 0L,
            id = id.toLong()
        )
    }

    override suspend fun updateDownloaded(id: Int, isDownloaded: Boolean) {
        appDatabase.appDatabaseQueries.updateDownloaded(
            isDownloaded = if (isDownloaded) 1L else 0L,
            id = id.toLong()
        )
    }

    override suspend fun updateReview(id: Int, rating: Int, review: String) {
        appDatabase.appDatabaseQueries.updateReview(
            rating = rating.toLong(),
            review = review,
            id = id.toLong()
        )
    }

    override suspend fun delete(id: Int) {
        appDatabase.appDatabaseQueries
            .delete(id.toLong())
    }

}