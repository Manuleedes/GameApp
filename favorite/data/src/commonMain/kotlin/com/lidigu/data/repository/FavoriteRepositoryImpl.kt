package com.lidigu.data.repository

import com.lidigu.common.domain.model.Game
import com.lidigu.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(
    private val appDatabase: AppDatabase
): FavoriteRepository {
    override fun getAllGames(): Flow<List<Game>> {
      return  appDatabase.appDatabaseQueries
            .getAllGames()
            .mapToList(Dispatchers.IO)
            .map{
               it.map{
                   Game(
                       id = it.id.toInt(),
                       name = it.name,
                       imageBackground = it.image
                   )
               }
            }

    }

    override suspend fun upsert(id: Int, name: String, image: String) {
        appDatabase.appDatabaseQueries
            .upsert(id.toLong(), image, name)
    }

    override suspend fun delete(id: Int) {
        appDatabase.appDatabaseQueries
            .delete(id.toLong())
    }
}