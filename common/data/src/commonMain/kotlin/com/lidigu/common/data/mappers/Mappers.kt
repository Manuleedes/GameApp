package com.lidigu.common.data.mappers

import com.lidigu.common.domain.model.Game
import com.lidigu.coreNetwork.model.game.Result

fun List<Result>.toDomainListOfGames(): List<Game> = mapNotNull {
    val id = it.id ?: return@mapNotNull null
    Game(
        id = id,
        name = it.name,
        imageBackground = it.background_image
    )
}