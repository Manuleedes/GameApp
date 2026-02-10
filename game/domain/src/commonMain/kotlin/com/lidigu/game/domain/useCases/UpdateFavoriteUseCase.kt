package com.lidigu.game.domain.useCases

import com.lidigu.game.domain.repository.GameRepository

class UpdateFavoriteUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) =
        gameRepository.updateFavorite(id, isFavorite)
}
