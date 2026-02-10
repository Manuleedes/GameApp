package com.lidigu.game.domain.model

data class DownloadProgress(
    val gameId: Int,
    val gameName: String,
    val progress: Float, // 0.0 to 1.0
    val status: DownloadStatus,
    val downloadedBytes: Long = 0,
    val totalBytes: Long = 0
)

enum class DownloadStatus {
    PENDING,
    DOWNLOADING,
    COMPLETED,
    FAILED,
    CANCELLED
}
