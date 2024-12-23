package com.ibra.dev.stormcitiesapp.home.presentation.usecase

import androidx.paging.PagingData
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import kotlinx.coroutines.flow.Flow

interface GetCitiesPagedUseCase {

    fun invoke(onlyFavorite: Boolean): Flow<PagingData<CityDto>>
    fun invoke(query: String, onlyFavorite: Boolean): Flow<PagingData<CityDto>>
}