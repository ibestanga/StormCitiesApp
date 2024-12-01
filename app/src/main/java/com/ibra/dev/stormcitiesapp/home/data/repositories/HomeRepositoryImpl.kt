package com.ibra.dev.stormcitiesapp.home.data.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class HomeRepositoryImpl(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSourceImpl: HomeLocalDataSource
) : HomeRepository {
    companion object {
        const val TAG = "HomeRepositoryImpl"
    }

    override suspend fun fetchCities(): Flow<PagingData<CityEntity>> {
        if (!localDataSourceImpl.hasCities()) {
            Log.i(TAG, "fetchCities: uptade online")
            val rawCities = remoteDataSource.getCitiesList()

           val resultList = if (rawCities.isSuccessful) {
                Log.i(TAG, "fetchCities: request success")
                rawCities.body().orEmpty()
            } else {
               throw HttpException(rawCities)
            }

            localDataSourceImpl.insertSortedCities(resultList).also {
                Log.i(TAG, "fetchCities: insert result list")
            }
        }

        return getPagedCities()
    }

    override fun getCitiesPage(): PagingSource<Int, CityEntity> {

       return localDataSourceImpl.getPagedCities().also {
            Log.i(TAG, "getPagedCities: get citiesList")
        }
    }

    override suspend fun filterByName(nameCity: String): PagingSource<Int, CityEntity> {
        return localDataSourceImpl.getCitiesByName(nameCity).also {
            Log.i(TAG, "getPagedCities: get queryFilter")
        }
    }

    private fun getPagedCities(nameCity: String? = null): Flow<PagingData<CityEntity>> {
        Log.i(TAG, "getPagedCities: pager flow")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                nameCity?.let {
                    localDataSourceImpl.getCitiesByName(it).also {
                        Log.i(TAG, "getPagedCities: get queryFilter")
                    }
                } ?: localDataSourceImpl.getPagedCities().also {
                    Log.i(TAG, "getPagedCities: get citiesList")
                }
            }
        ).flow
    }
}