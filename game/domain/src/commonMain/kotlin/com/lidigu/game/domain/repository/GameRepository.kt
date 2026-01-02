package com.lidigu.game.domain.repository

import com.lidigu.common.domain.model.Game


interface GameRepository {
    suspend fun getGames(): Result<List<Game>>
}