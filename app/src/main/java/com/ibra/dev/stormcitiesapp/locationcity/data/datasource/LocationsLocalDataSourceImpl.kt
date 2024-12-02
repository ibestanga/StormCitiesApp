package com.ibra.dev.stormcitiesapp.locationcity.data.datasource

import com.ibra.dev.stormcitiesapp.commons.database.CityDao
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

class LocationsLocalDataSourceImpl(
    private val dao: CityDao
) : LocationsLocalDataSource {
    override suspend fun getCityById(id: Int): CityEntity {
        return dao.getCityById(id)
    }
}