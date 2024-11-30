package com.ibra.dev.stormcitiesapp.home.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.ibra.dev.stormcitiesapp.home.data.entities.toDto
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetCitiesPagedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCitiesPagedUseCaseImpl(
    private val repository: HomeRepository
) : GetCitiesPagedUseCase {
    override suspend fun invoke(): Flow<PagingData<CityDto>> =
        repository.fetchCities().map { cities ->
            cities.map { entity ->
                entity.toDto()
            }
        }

    override suspend fun invoke(query: String): Flow<PagingData<CityDto>> =
        repository.filterByName(query).map { cities ->
            cities.map { entity ->
                entity.toDto()
            }
        }
}