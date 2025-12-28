package com.lidigu.game.data.mappers

import com.lidigu.coreNetwork.model.game.Result
import com.lidigu.game.domain.model.Game

fun List<Result>.toDomainListOfGames(): List<Game> = map {
    Game(
        id = it.id,
        name = it.name,
        imageUrl = it.imageBackground
    )
}