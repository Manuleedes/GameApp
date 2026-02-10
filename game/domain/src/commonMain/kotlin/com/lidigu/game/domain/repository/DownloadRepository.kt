package com.lidigu.game.domain.repository

import com.lidigu.game.domain.model.DownloadProgress
import kotlinx.coroutines.flow.Flow

interface DownloadRepository {
    suspend fun downloadGame(gameId: Int, gameUrl: String, gameName: String): Flow<DownloadProgress>
    suspend fun cancelDownload(gameId: Int)
    suspend fun getDownloadProgress(gameId: Int): Flow<DownloadProgress?>
    suspend fun getAllDownloads(): Flow<List<DownloadProgress>>
}
