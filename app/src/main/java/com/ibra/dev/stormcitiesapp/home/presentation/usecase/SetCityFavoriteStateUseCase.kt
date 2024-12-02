package com.ibra.dev.stormcitiesapp.home.presentation.usecase

interface SetCityFavoriteStateUseCase {

    suspend fun invoke(cityId: Int, isFavorite: Boolean)
}