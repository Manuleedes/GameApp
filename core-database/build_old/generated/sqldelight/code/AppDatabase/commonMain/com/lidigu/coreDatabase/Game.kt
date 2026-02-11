package com.lidigu.coreDatabase

import kotlin.Long
import kotlin.String

public data class Game(
  public val id: Long,
  public val image: String,
  public val name: String,
  public val isFavorite: Long,
  public val isDownloaded: Long,
  public val rating: Long?,
  public val review: String?,
)
