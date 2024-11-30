package com.ibra.dev.stormcitiesapp.home.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ibra.dev.stormcitiesapp.home.data.entities.toDto
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetAllCitiesPagedUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllCitiesPagedUseCaseImpl(
    private val repository: HomeRepository
) : GetAllCitiesPagedUseCase {
    override suspend fun invoke(): Flow<PagingData<CityDto>> =
        repository.fetchCities().map { cities ->
            cities.map { entity ->
                entity.toDto()
            }

        }
}