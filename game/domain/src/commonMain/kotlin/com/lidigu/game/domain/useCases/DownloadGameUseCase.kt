package com.lidigu.game.domain.useCases

import com.lidigu.game.domain.model.DownloadProgress
import com.lidigu.game.domain.repository.DownloadRepository
import com.lidigu.game.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class DownloadGameUseCase(
    private val downloadRepository: DownloadRepository,
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(
        gameId: Int, 
        gameUrl: String, 
        gameName: String
    ): Flow<DownloadProgress> {
        return downloadRepository.downloadGame(gameId, gameUrl, gameName)
    }
    
    suspend fun markAsDownloaded(gameId: Int, isDownloaded: Boolean) {
        gameRepository.updateDownloaded(gameId, isDownloaded)
    }
}
