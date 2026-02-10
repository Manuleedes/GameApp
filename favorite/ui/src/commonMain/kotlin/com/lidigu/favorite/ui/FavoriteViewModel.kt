package com.lidigu.favorite.ui


import androidx.lifecycle.ViewModel
import com.lidigu.favorite.domain.useCases.DeleteUseCase
import com.lidigu.favorite.domain.useCases.GetAllLocalCasedGamesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map


class FavoriteViewModel(
    private val getAllLocalCasedGamesUseCase: GetAllLocalCasedGamesUseCase,
    private val deleteUseCase: DeleteUseCase
): ViewModel() {
   val allLocalGames = getAllLocalCasedGamesUseCase.invoke().stateIn(
       viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
   )

   val favoriteGames = allLocalGames.map { games -> games.filter { it.isFavorite } }
       .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

   val downloadedGames = allLocalGames.map { games -> games.filter { it.isDownloaded } }
       .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

   val reviewedGames = allLocalGames.map { games -> games.filter { it.review != null } }
       .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun delete(id: Int) = viewModelScope.launch {
        deleteUseCase.invoke(id)
    }
}