package com.lidigu.favorite.domain.useCases

import com.lidigu.favorite.domain.repository.FavoriteRepository

class GetAllLocalCasedGamesUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() = favoriteRepository.getAllGames()
}