package com.ibra.dev.stormcitiesapp.home.domain.usecases

import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.SetCityFavoriteStateUseCase

class SetCityFavoriteStateUseCaseImpl(
    private val repository: HomeRepository
) : SetCityFavoriteStateUseCase {

    override suspend fun invoke(cityId: Int, isFavorite: Boolean) {
        repository.setFavoriteState(cityId, isFavorite)
    }
}