package com.lidigu.favorite.domain.useCases

import com.lidigu.favorite.domain.repository.FavoriteRepository

class UpsertUseCase(
    private val favoriteRepository: FavoriteRepository
) {

   suspend operator fun invoke(id:Int, image: String, name: String) =
        favoriteRepository.insertGame(id, image = image, name = name, isFavorite = true)
}