package com.lidigu.game.data.mappers

import com.lidigu.coreNetwork.model.gameDetails.GameDetailsResponse
import com.lidigu.game.domain.model.Developer
import com.lidigu.game.domain.model.GameDetails
import com.lidigu.game.domain.model.Platform
import com.lidigu.game.domain.model.Store
import com.lidigu.game.domain.model.Tag


fun GameDetailsResponse.toDomainGameDetails(): GameDetails {
    return GameDetails(
        id = id ?: 0,
        name = name ?: "Unknown Game",
        description = description_raw ?: "",

        backgroundImage = background_image ?: "",
        additionalImage = background_image_additional ?: "",

        platforms = platforms.orEmpty().mapNotNull { platformItem ->
            platformItem.platform?.let { platform ->
                Platform(
                    name = platform.name ?: "Unknown",
                    image = platform.image_background ?: ""
                )
            }
        },

        stores = stores.orEmpty().mapNotNull { storeItem ->
            storeItem.store?.let { store ->
                Store(
                    name = store.name ?: "Unknown Store",
                    image = store.image_background ?: "",
                    gameCount = store.games_count ?: 0,
                    domain = store.domain ?: ""
                )
            }
        },

        developers = developers.orEmpty().mapNotNull { developer ->
            developer?.let {
                Developer(
                    name = it.name ?: "Unknown Developer",
                    image = it.image_background ?: "",
                    gameCount = it.games_count ?: 0
                )
            }
        },

        tags = tags.orEmpty().mapNotNull { tag ->
            tag?.let {
                Tag(
                    name = it.name ?: "Unknown",
                    image = it.image_background ?: ""
                )
            }
        }
    )
}

