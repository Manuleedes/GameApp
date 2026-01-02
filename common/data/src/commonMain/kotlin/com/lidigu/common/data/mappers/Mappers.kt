package com.lidigu.common.data.mappers

import com.lidigu.common.domain.model.Game
import com.lidigu.coreNetwork.model.game.Result

fun List<Result>.toDomainListOfGames(): List<Game> = map {
    Game(
        id = it.id,
        name = it.name,
        imageBackground = it.background_image
    )
}