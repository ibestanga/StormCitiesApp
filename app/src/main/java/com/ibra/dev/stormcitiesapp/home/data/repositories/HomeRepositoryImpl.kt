package com.ibra.dev.stormcitiesapp.home.data.repositories

import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.RemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

class HomeRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : HomeRepository {
    override suspend fun getCitiesList(): List<CityEntity> {
        val response = remoteDataSource.getCitiesList()
        return if (response.isSuccessful) response.body().orEmpty() else emptyList()
    }
}