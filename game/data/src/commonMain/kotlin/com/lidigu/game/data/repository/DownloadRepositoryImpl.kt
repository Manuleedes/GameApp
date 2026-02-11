package com.lidigu.game.data.repository

import com.lidigu.game.domain.model.DownloadProgress
import com.lidigu.game.domain.model.DownloadStatus
import com.lidigu.game.domain.repository.DownloadRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString
import kotlinx.io.readLine
import kotlinx.datetime.Clock

class DownloadRepositoryImpl(
    private val httpClient: HttpClient
) : DownloadRepository {
    
    private val activeDownloads = mutableMapOf<Int, MutableStateFlow<DownloadProgress>>()
    
    override suspend fun downloadGame(
        gameId: Int,
        gameUrl: String,
        gameName: String
    ): Flow<DownloadProgress> = flow {
        val progressFlow = MutableStateFlow(
            DownloadProgress(
                gameId = gameId,
                gameName = gameName,
                progress = 0f,
                status = DownloadStatus.PENDING
            )
        )
        
        activeDownloads[gameId] = progressFlow
        emit(progressFlow.value)
        
        try {
            // Update status to downloading
            progressFlow.value = progressFlow.value.copy(status = DownloadStatus.DOWNLOADING)
            emit(progressFlow.value)


            // Since FreeToGame doesn't provide direct download links,
            // we'll save the game metadata and mark it as available offline
            val downloadsDir = getDownloadsDirectory()
            val downloadsPath = Path(downloadsDir)
            if (!SystemFileSystem.exists(downloadsPath)) {
                SystemFileSystem.createDirectories(downloadsPath)
            }
            
            val gameInfoFile = Path("$downloadsDir/game_$gameId.txt")
            
            // Save game information
            withContext(Dispatchers.Default) {
                val sink = SystemFileSystem.sink(gameInfoFile).buffered()
                try {
                    sink.writeString("Game: $gameName\n")
                    sink.writeString("URL: $gameUrl\n")
                    sink.writeString("Downloaded: ${Clock.System.now()}\n")
                    sink.writeString("Offline Access: Enabled\n")
                } finally {
                    sink.close()
                }
            }
            
            // Simulate download progress for demonstration
            for (i in 1..10) {
                if (activeDownloads[gameId] == null) {
                    // Download was cancelled
                    progressFlow.value = progressFlow.value.copy(
                        status = DownloadStatus.CANCELLED,
                        progress = 0f
                    )
                    emit(progressFlow.value)
                    return@flow
                }
                
                kotlinx.coroutines.delay(300)
                val progress = i / 10f
                progressFlow.value = progressFlow.value.copy(
                    progress = progress,
                    downloadedBytes = (progress * 1000000).toLong(),
                    totalBytes = 1000000L
                )
                emit(progressFlow.value)
            }
            
            // Mark as completed
            progressFlow.value = progressFlow.value.copy(
                status = DownloadStatus.COMPLETED,
                progress = 1f
            )
            emit(progressFlow.value)
            
        } catch (e: Exception) {
            progressFlow.value = progressFlow.value.copy(
                status = DownloadStatus.FAILED,
                progress = 0f
            )
            emit(progressFlow.value)
        } finally {
            activeDownloads.remove(gameId)
        }
    }
    
    override suspend fun cancelDownload(gameId: Int) {
        activeDownloads.remove(gameId)
    }
    
    override suspend fun getDownloadProgress(gameId: Int): Flow<DownloadProgress?> = flow {
        emit(activeDownloads[gameId]?.value)
    }
    
    override suspend fun getAllDownloads(): Flow<List<DownloadProgress>> = flow {
        emit(activeDownloads.values.map { it.value })
    }

    override suspend fun getDownloadedGameUrl(gameId: Int): String? = withContext(Dispatchers.Default) {
        val downloadsDir = getDownloadsDirectory()
        val gameInfoFile = Path("$downloadsDir/game_$gameId.txt")
        
        if (!SystemFileSystem.exists(gameInfoFile)) return@withContext null
        
        try {
            val source = SystemFileSystem.source(gameInfoFile).buffered()
            var gameUrl: String? = null
            
            while (!source.exhausted()) {
                val line = source.readLine() ?: break
                if (line.startsWith("URL: ")) {
                    gameUrl = line.removePrefix("URL: ").trim()
                    break
                }
            }
            source.close()
            gameUrl
        } catch (e: Exception) {
            null
        }
    }

    private fun getDownloadsDirectory(): String {
        // Platform-specific downloads directory
        return when {
            // You can customize this based on platform
            else -> System.getProperty("user.home") + "/Downloads/GameApp"
        }
    }
}
