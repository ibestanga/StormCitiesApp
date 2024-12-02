package com.ibra.dev.stormcitiesapp.locationcity.domain.repositories

import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

interface LocationsRepository {

    suspend fun getCityById(id: Int): CityEntity
}