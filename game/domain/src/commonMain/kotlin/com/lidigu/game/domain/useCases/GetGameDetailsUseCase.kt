package com.lidigu.game.domain.useCases

import com.lidigu.game.domain.model.GameDetails
import com.lidigu.game.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetGameDetailsUseCase(private val gameRepository: GameRepository) {

    operator fun invoke(id: Int) = flow<Result<GameDetails>> {
        emit(gameRepository.getDetails(id))
    }.catch { error ->
        emit(Result.failure(error))
    }.flowOn(Dispatchers.IO)
}