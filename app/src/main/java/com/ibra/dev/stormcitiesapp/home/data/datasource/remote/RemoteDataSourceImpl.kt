package com.ibra.dev.stormcitiesapp.home.data.datasource.remote

import com.ibra.dev.stormcitiesapp.home.data.api.HomeApi
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import retrofit2.Response

class RemoteDataSourceImpl(
    private val api: HomeApi
): RemoteDataSource {
    override suspend fun getCitiesList(): Response<List<CityEntity>> = api.getCitiesList()
}