package com.lidigu.game.domain.useCases

import com.lidigu.common.domain.model.Game
import com.lidigu.game.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GetLocalGameUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(id: Int): Flow<Game?> =
        gameRepository.getLocalGame(id)
}
