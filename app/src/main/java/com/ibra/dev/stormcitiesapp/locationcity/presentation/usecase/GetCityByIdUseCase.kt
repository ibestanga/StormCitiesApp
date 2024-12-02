package com.ibra.dev.stormcitiesapp.locationcity.presentation.usecase

import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto

interface GetCityByIdUseCase {

   suspend fun invoke(id: Int) : CityDto
}