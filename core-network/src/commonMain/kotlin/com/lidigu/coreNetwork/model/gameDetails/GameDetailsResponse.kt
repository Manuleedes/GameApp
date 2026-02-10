package com.lidigu.coreNetwork.model.gameDetails

import com.lidigu.coreNetwork.model.game.FreeToGameResult
import kotlinx.serialization.Serializable

// FreeToGame API returns the game object directly for details endpoint
typealias GameDetailsResponse = FreeToGameDetailsWrapper

@Serializable
data class FreeToGameDetailsWrapper(
    val id: Int?,
    val title: String?,
    val thumbnail: String?,
    val status: String?,
    val short_description: String?,
    val description: String?,
    val game_url: String?,
    val genre: String?,
    val platform: String?,
    val publisher: String?,
    val developer: String?,
    val release_date: String?,
    val freetogame_profile_url: String?,
    val minimum_system_requirements: SystemRequirements? = null,
    val screenshots: List<Screenshot>? = null
)

@Serializable
data class SystemRequirements(
    val os: String? = null,
    val processor: String? = null,
    val memory: String? = null,
    val graphics: String? = null,
    val storage: String? = null
)

@Serializable
data class Screenshot(
    val id: Int?,
    val image: String?
)