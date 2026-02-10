package com.lidigu.game.domain.useCases

import com.lidigu.game.domain.repository.GameRepository

class ReviewGameUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(id: Int, rating: Int, review: String) =
        gameRepository.updateReview(id, rating, review)
}
