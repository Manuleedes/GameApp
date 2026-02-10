package com.lidigu.common.domain.model

data class Game(
    val id : Int,
    val name : String?,
    val imageBackground: String?,
    val isFavorite: Boolean = false,
    val isDownloaded: Boolean = false,
    val rating: Int? = null,
    val review: String? = null
)