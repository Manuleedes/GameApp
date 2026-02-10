package com.lidigu.game.domain.model

data class GameDetails (
    val name: String?,
    val id: Int,
    val description: String?,
    val backgroundImage: String?,
    val additionalImage: String?,
    val platforms: List<Platform>,
    val stores: List<Store>,
    val developers: List<Developer>,
    val tags: List<Tag>,
    val website: String?
)

data class Platform(
    val name: String?,
    val image: String?
)

data class Store(
    val name: String?,
    val image: String?,
    val gameCount: Int?,
    val domain: String?,
    val url: String?
)

data class Developer(
    val name: String?,
    val image: String?,
    val gameCount: Int?
)

data class Tag(
    val name: String?,
    val image: String?
)