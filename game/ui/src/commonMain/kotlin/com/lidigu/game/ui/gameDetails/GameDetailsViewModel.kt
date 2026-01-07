package com.lidigu.game.ui.gameDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidigu.game.domain.model.GameDetails
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


class GameDetailsViewModel(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val saveGameUseCase: SaveGameUseCase,
    private val deleteUseCase: DeleteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameDetailsScreen.UiState())
    val uiState = _uiState.asStateFlow()


    fun getGameDetails(id: Int) {
        getGameDetailsUseCase.invoke(id)
            .onStart { _uiState.update { GameDetailsScreen.UiState(isLoading = true) } }
            .onEach { result ->
                result.onSuccess { data ->
                    _uiState.update { GameDetailsScreen.UiState(data = data) }
                }.onFailure { error ->
                    _uiState.update { GameDetailsScreen.UiState(error = error.message.toString()) }
                }
            }.launchIn(viewModelScope)
    }

    fun save(id: Int, image: String, name: String) = viewModelScope.launch {
        saveGameUseCase.invoke(id, image, name)
        _uiState.update { it.copy(isSaved = true)}
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
            deleteUseCase.invoke(id)
        } else {
            // Add to favorites
            saveGameUseCase.invoke(id, image, name)
        }
        // Update UI state
        _uiState.update { it.copy(isSaved = !currentlySaved) }
    }





}


data object GameDetailsScreen {

    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: GameDetails? = null,
        val isDeleted: Boolean = false,
        val isSaved: Boolean = false
    )
}