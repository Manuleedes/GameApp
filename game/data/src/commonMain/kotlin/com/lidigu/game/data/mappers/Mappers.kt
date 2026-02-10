package com.lidigu.game.data.mappers

import com.lidigu.coreNetwork.model.gameDetails.GameDetailsResponse
import com.lidigu.game.domain.model.Developer
import com.lidigu.game.domain.model.GameDetails
import com.lidigu.game.domain.model.Platform
import com.lidigu.game.domain.model.Store
import com.lidigu.game.domain.model.Tag


fun GameDetailsResponse.toDomainGameDetails(): GameDetails {
    return GameDetails(
        id = this.id ?: 0,
        name = this.title ?: "Unknown Game",
        description = this.description ?: this.short_description ?: "",

        backgroundImage = this.thumbnail ?: "",
        additionalImage = this.screenshots?.firstOrNull()?.image ?: "",

        platforms = listOfNotNull(
            this.platform?.let {
                Platform(
                    name = it,
                    image = ""
                )
            }
        ),

        stores = listOfNotNull(
            this.game_url?.let {
                Store(
                    name = "FreeToGame",
                    image = "",
                    gameCount = 1,
                    domain = "freetogame.com",
                    url = it
                )
            }
        ),

        developers = listOfNotNull(
            this.developer?.let {
                Developer(
                    name = it,
                    image = "",
                    gameCount = 1
                )
            }
        ),

        tags = listOfNotNull(
            this.genre?.let {
                Tag(
                    name = it,
                    image = ""
                )
            }
        ),
        website = this.freetogame_profile_url
    )
}

