package com.lidigu.game.domain.repository

import com.lidigu.game.domain.model.Game

interface GameRepository {
    suspend fun getGames(): Result<List<Game>>
}