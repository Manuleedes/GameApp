package com.lidigu.search.domain.repository

import com.lidigu.common.domain.model.Game

interface SearchRepository {
    suspend fun search(q: String): Result<List<Game>>
}