package com.ibra.dev.stormcitiesapp.locationcity.domain.usecase

import com.ibra.dev.stormcitiesapp.home.data.entities.toDto
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.locationcity.domain.repositories.LocationsRepository
import com.ibra.dev.stormcitiesapp.locationcity.presentation.usecase.GetCityByIdUseCase

class GetCityByIdUseCaseImpl(
    private val repository: LocationsRepository
) : GetCityByIdUseCase {
    override suspend fun invoke(id: Int): CityDto {
        return repository.getCityById(id).toDto()
    }
}