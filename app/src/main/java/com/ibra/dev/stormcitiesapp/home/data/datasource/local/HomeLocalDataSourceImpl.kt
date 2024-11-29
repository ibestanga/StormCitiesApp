package com.ibra.dev.stormcitiesapp.home.data.datasource.local

import com.ibra.dev.stormcitiesapp.commons.database.CityDao
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

class HomeLocalDataSourceImpl(
    private val dao: CityDao
): HomeLocalDataSource {
    override suspend fun getAllCities(): List<CityEntity> {
        return dao.getSortedCities()
    }

    override suspend fun insertSortedCities(sortedCities: List<CityEntity>) {
       dao.insertSortedCities(sortedCities)
    }
}