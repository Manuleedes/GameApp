package com.lidigu.game.domain.repository

import com.lidigu.common.domain.model.Game
import com.lidigu.game.domain.model.GameDetails
import kotlinx.coroutines.flow.Flow


interface GameRepository {
    suspend fun getGames(): Result<List<Game>>
    suspend fun getDetails(id: Int): Result<GameDetails>

    fun getLocalGame(id: Int): Flow<Game?>

    suspend fun saveGame(id: Int, image: String, name: String, isFavorite: Boolean = false, isDownloaded: Boolean = false)

    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    suspend fun updateDownloaded(id: Int, isDownloaded: Boolean)

    suspend fun updateReview(id: Int, rating: Int, review: String)

    suspend fun delete(id: Int)
}