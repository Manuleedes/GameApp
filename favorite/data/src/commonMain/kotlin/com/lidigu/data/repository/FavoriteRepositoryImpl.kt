package com.lidigu.data.repository

import app.cash.sqldelight.coroutines.asFlow
import com.lidigu.common.domain.model.Game
import com.lidigu.coreDatabase.AppDatabase
import com.lidigu.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val appDatabase: AppDatabase
): FavoriteRepository {
    override fun getAllGames(): Flow<List<Game>> {
        return appDatabase.appDatabaseQueries
            .getAllGames()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map {
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

    override fun getGameById(id: Int): Flow<Game?> {
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

    override suspend fun insertGame(
        id: Int,
        name: String,
        image: String,
        isFavorite: Boolean,
        isDownloaded: Boolean,
        rating: Int?,
        review: String?
    ) {
        appDatabase.appDatabaseQueries.insertGame(
            id = id.toLong(),
            image = image,
            name = name,
            isFavorite = if (isFavorite) 1L else 0L,
            isDownloaded = if (isDownloaded) 1L else 0L,
            rating = rating?.toLong(),
            review = review
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


