package com.lidigu.favorite.ui


import androidx.lifecycle.ViewModel
import com.lidigu.favorite.domain.useCases.DeleteUseCase
import com.lidigu.favorite.domain.useCases.GetAllLocalCasedGamesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class FavoriteViewModel(
    private val getAllLocalCasedGamesUseCase: GetAllLocalCasedGamesUseCase,
    private val deleteUseCase: DeleteUseCase
): ViewModel() {
   val games = getAllLocalCasedGamesUseCase.invoke().stateIn(
       viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
   )

    fun delete(id: Int) = viewModelScope.launch {
        deleteUseCase.invoke(id)
    }
}