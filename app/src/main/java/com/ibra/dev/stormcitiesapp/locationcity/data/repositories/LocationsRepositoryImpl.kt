package com.ibra.dev.stormcitiesapp.locationcity.data.repositories

import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.locationcity.data.datasource.LocationsLocalDataSource
import com.ibra.dev.stormcitiesapp.locationcity.domain.repositories.LocationsRepository

class LocationsRepositoryImpl(
    private val localDataSource: LocationsLocalDataSource
) : LocationsRepository {
    override suspend fun getCityById(id: Int): CityEntity {
      return localDataSource.getCityById(id)
    }
}