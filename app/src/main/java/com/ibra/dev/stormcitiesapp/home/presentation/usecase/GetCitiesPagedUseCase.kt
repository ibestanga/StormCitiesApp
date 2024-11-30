package com.ibra.dev.stormcitiesapp.home.presentation.usecase

import androidx.paging.PagingData
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import kotlinx.coroutines.flow.Flow

interface GetCitiesPagedUseCase {

    suspend fun invoke(): Flow<PagingData<CityDto>>
    suspend fun invoke(query: String): Flow<PagingData<CityDto>>
}