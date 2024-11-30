package com.ibra.dev.stormcitiesapp.home.presentation.usecase

import androidx.paging.PagingData
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import kotlinx.coroutines.flow.Flow

interface GetAllCitiesPagedUseCase {

    suspend fun invoke() : Flow<PagingData<CityDto>>
}