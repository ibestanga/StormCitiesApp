package com.ibra.dev.stormcitiesapp.home.data.repositories

import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository

class HomeRepositoryImpl(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSourceImpl: HomeLocalDataSource
) : HomeRepository {
    override suspend fun getCitiesList(): List<CityEntity> {
        val existingCities = localDataSourceImpl.getAllCities()
        val sortedCities: List<CityEntity>

        if (existingCities.isEmpty()) {
            val rawCities = remoteDataSource.getCitiesList()

            sortedCities = if (rawCities.isSuccessful) {
                rawCities.body().orEmpty()
            } else {
                emptyList()
            }

            localDataSourceImpl.insertSortedCities(sortedCities)
        }

        return localDataSourceImpl.getAllCities()
    }
}