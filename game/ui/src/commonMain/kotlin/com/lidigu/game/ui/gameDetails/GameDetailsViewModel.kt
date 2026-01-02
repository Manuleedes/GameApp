package com.lidigu.game.ui.gameDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidigu.game.domain.model.GameDetails
import com.lidigu.game.domain.useCases.GetGameDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class GameDetailsViewModel(
    private val getGameDetailsUseCase: GetGameDetailsUseCase
): ViewModel() {
  private val _uiState = MutableStateFlow(GameDetailsScreen.UiState())
    val uiState = _uiState.asStateFlow()

    fun getGameDetails(id: Int){
        getGameDetailsUseCase.invoke(id)
            .onStart {
              _uiState.update { GameDetailsScreen.UiState(isLoading = true)
              }
            }.onEach { result ->
                result.onSuccess { data ->
                    _uiState.update { GameDetailsScreen.UiState(data = data) }

                }.onFailure { error ->
                    _uiState.update { GameDetailsScreen.UiState(error = error.message.toString()) }
                }
            }.launchIn(viewModelScope)
    }
}

data object GameDetailsScreen{
    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: GameDetails? = null
    )
}