package com.lidigu.coreNetwork.model.game


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val id: Int = 0,
    @SerialName("image_background")
    val imageBackground: String = "",
    @SerialName("name")
    val name: String = "",

)