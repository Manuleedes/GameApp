package com.lidigu.game.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lidigu.game.domain.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class GameViewModel(
    private val getGamesUseCase: T
): ViewModel() {
    private val _uiState = MutableStateFlow(GameScreen.UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getGames()
    }
    fun getGames() = getGamesUseCase.invoke()
        .onStart {
            _uiState.update { GameScreen.UiState(isLoading = true) }
        }.onEach { result ->
            result.onSuccess { data ->
                    _uiState.update { GameScreen.UiState(data = data) }
            }.onFailure { error ->
                _uiState.update { GameScreen.UiState(error = error.message.toString()) }
            }

        }.launchIn(viewModelScope)

}
object GameScreen{
    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: List<Game>? = null
    )
}