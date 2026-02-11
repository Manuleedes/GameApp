package com.lidigu.game.domain.useCases

import com.lidigu.game.domain.repository.DownloadRepository

class GetDownloadedGameUrlUseCase(
    private val downloadRepository: DownloadRepository
) {
    suspend operator fun invoke(gameId: Int): String? {
        return downloadRepository.getDownloadedGameUrl(gameId)
    }
}
