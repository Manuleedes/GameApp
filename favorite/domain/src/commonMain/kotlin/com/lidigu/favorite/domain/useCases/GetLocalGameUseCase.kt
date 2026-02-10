package com.lidigu.favorite.domain.useCases

import com.lidigu.common.domain.model.Game
import com.lidigu.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetLocalGameUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(id: Int): Flow<Game?> =
        favoriteRepository.getGameById(id)
}
