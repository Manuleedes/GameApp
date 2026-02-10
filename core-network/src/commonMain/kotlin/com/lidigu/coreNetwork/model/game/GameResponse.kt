package com.lidigu.coreNetwork.model.game

import kotlinx.serialization.Serializable

// FreeToGame API returns an array directly, so we wrap it
typealias GameResponse = GameResponseWrapper

@Serializable
data class GameResponseWrapper(
    val games: List<FreeToGameResult> = emptyList()
)

@Serializable
data class FreeToGameResult(
    val id: Int?,
    val title: String?,
    val thumbnail: String?,
    val short_description: String?,
    val game_url: String?,
    val genre: String?,
    val platform: String?,
    val publisher: String?,
    val developer: String?,
    val release_date: String?,
    val freetogame_profile_url: String?
)