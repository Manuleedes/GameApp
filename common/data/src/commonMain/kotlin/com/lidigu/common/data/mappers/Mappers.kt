package com.lidigu.common.data.mappers

import com.lidigu.common.domain.model.Game
import com.lidigu.coreNetwork.model.game.FreeToGameResult

fun List<FreeToGameResult>.toDomainListOfGames(): List<Game> = mapNotNull {
    val id = it.id ?: return@mapNotNull null
    Game(
        id = id,
        name = it.title,
        imageBackground = it.thumbnail
    )
}