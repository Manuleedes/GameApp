package com.lidigu.game.domain.repository

import com.lidigu.common.domain.model.Game
import com.lidigu.game.domain.model.GameDetails


interface GameRepository {
    suspend fun getGames(): Result<List<Game>>

    suspend fun getDetails(id: Int): Result<GameDetails>
}