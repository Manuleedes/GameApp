package com.lidigu.game.ui.gameDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidigu.game.domain.model.GameDetails
import com.lidigu.game.domain.model.DownloadProgress
import com.lidigu.game.domain.model.DownloadStatus
import com.lidigu.common.domain.model.Game
import com.lidigu.game.domain.useCases.DeleteUseCase
import com.lidigu.game.domain.useCases.GetGameDetailsUseCase
import com.lidigu.game.domain.useCases.SaveGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.lidigu.game.domain.useCases.DownloadGameUseCase
import com.lidigu.game.domain.useCases.ReviewGameUseCase
import com.lidigu.game.domain.useCases.UpdateFavoriteUseCase
import com.lidigu.game.domain.useCases.GetLocalGameUseCase


class GameDetailsViewModel(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val saveGameUseCase: SaveGameUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val downloadGameUseCase: DownloadGameUseCase,
    private val reviewGameUseCase: ReviewGameUseCase,
    private val getLocalGameUseCase: GetLocalGameUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameDetailsScreen.UiState())
    val uiState = _uiState.asStateFlow()


    fun getGameDetails(id: Int) {
        getGameDetailsUseCase.invoke(id)
            .onStart { _uiState.update { state: GameDetailsScreen.UiState -> state.copy(isLoading = true) } }
            .onEach { result ->
                _uiState.update { state ->
                    result.fold(
                        onSuccess = { data -> state.copy(data = data, isLoading = false) },
                        onFailure = { error -> state.copy(error = error.message.toString(), isLoading = false) }
                    )
                }
            }.launchIn(viewModelScope)

        getLocalGameUseCase.invoke(id)
            .onEach { localGame: Game? ->
                _uiState.update { state: GameDetailsScreen.UiState ->
                    state.copy(
                        isSaved = localGame?.isFavorite ?: false,
                        isDownloaded = localGame?.isDownloaded ?: false,
                        rating = localGame?.rating,
                        review = localGame?.review
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun save(id: Int, image: String, name: String) = viewModelScope.launch {
        saveGameUseCase.invoke(id, image, name, isFavorite = true)
    }

    fun toggleDownload(id: Int, image: String, name: String, gameUrl: String) = viewModelScope.launch {
        val currentlyDownloaded = _uiState.value.isDownloaded
        
        if (!currentlyDownloaded) {
            // Start download
            saveGameUseCase.invoke(id, image, name, isDownloaded = false)
            downloadGameUseCase.invoke(id, gameUrl, name)
                .onEach { progress ->
                    _uiState.update { state ->
                        state.copy(
                            downloadProgress = progress,
                            isDownloading = progress.status == DownloadStatus.DOWNLOADING
                        )
                    }
                    
                    // When download completes, mark as downloaded
                    if (progress.status == DownloadStatus.COMPLETED) {
                        downloadGameUseCase.markAsDownloaded(id, true)
                    }
                }
                .launchIn(viewModelScope)
        } else {
            // Remove downloaded status
            downloadGameUseCase.markAsDownloaded(id, false)
        }
    }

    fun saveReview(id: Int, image: String, name: String, rating: Int, review: String) = viewModelScope.launch {
        saveGameUseCase.invoke(id, image, name)
        reviewGameUseCase.invoke(id, rating, review)
    }

    fun delete(id: Int) = viewModelScope.launch {
        runCatching {
            deleteUseCase.invoke(id)
        }.onSuccess {
            _uiState.update { it.copy(isDeleted = true) }
        }.onFailure { error ->
            _uiState.update { it.copy(error = error.message ?: "Delete failed") }
        }
    }
    fun consumeDeleteEvent() {
        _uiState.update { it.copy(isDeleted = false) }
    }

    fun toggleFavorite(id: Int, image: String, name: String) = viewModelScope.launch {
        val currentlySaved = _uiState.value.isSaved
        if (currentlySaved) {
            if (_uiState.value.isDownloaded || _uiState.value.isDownloading || _uiState.value.review != null) {
                updateFavoriteUseCase.invoke(id, false)
            } else {
                deleteUseCase.invoke(id)
            }
        } else {
            saveGameUseCase.invoke(id, image, name, isFavorite = true)
            updateFavoriteUseCase.invoke(id, true)
        }
    }





}


data object GameDetailsScreen {

    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: GameDetails? = null,
        val isDeleted: Boolean = false,
        val isSaved: Boolean = false,
        val isDownloaded: Boolean = false,
        val rating: Int? = null,
        val review: String? = null,
        val isDownloading: Boolean = false,
        val downloadProgress: DownloadProgress? = null
    )
}