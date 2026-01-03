package com.lidigu.favorite.domain.useCases

import com.lidigu.favorite.domain.repository.FavoriteRepository

class DeleteUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun  invoke(id: Int) = favoriteRepository.delete(id)
}