package com.ibra.dev.stormcitiesapp.home.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSourceImpl: HomeLocalDataSource
) : HomeRepository {
    override suspend fun fetchCities(): Flow<PagingData<CityEntity>> {
        val sortedCities: List<CityEntity>

        if (!localDataSourceImpl.hasCities()) {
            val rawCities = remoteDataSource.getCitiesList()

            sortedCities = if (rawCities.isSuccessful) {
                rawCities.body().orEmpty()
            } else {
                emptyList()
            }

            localDataSourceImpl.insertSortedCities(sortedCities)
        }

        return getPagedCities()
    }

    override suspend fun filterByName(nameCity: String): Flow<PagingData<CityEntity>> {
       return getPagedCities(nameCity)
    }

    private fun getPagedCities(nameCity: String? = null): Flow<PagingData<CityEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                nameCity?.let {
                    localDataSourceImpl.getCitiesByName(it)
                } ?: localDataSourceImpl.getPagedCities()
            }
        ).flow
    }
}