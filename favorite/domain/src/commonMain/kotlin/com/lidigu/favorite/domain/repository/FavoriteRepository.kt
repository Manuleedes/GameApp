package com.lidigu.favorite.domain.repository

import com.lidigu.common.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getAllGames(): Flow<List<Game>>

    fun getGameById(id: Int): Flow<Game?>

    suspend fun insertGame(id: Int, name: String, image: String, isFavorite: Boolean = false, isDownloaded: Boolean = false, rating: Int? = null, review: String? = null)

    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    suspend fun updateDownloaded(id: Int, isDownloaded: Boolean)

    suspend fun updateReview(id: Int, rating: Int, review: String)

    suspend fun delete(id: Int)
}

