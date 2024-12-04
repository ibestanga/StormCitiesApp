package com.ibra.dev.stormcitiesapp.home.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ibra.dev.stormcitiesapp.commons.extfunc.orZero
import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import kotlinx.coroutines.CompletableDeferred
import retrofit2.HttpException

class CityPagingSource(
    private val localDataSource: HomeLocalDataSource,
    private val remoteLocalDataSource: HomeRemoteDataSource,
) : PagingSource<Int, CityEntity>() {
    private val promise = CompletableDeferred<Unit>()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityEntity> {
        return try {
            val offset = params.key.orZero()
            val limit = params.loadSize

            promise.complete(fetchRemoteIfNecessary())
            promise.await()
            val cities = localDataSource.getPagedCities(limit, offset)

            LoadResult.Page(
                data = cities,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (cities.isEmpty()) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CityEntity>): Int? {
        return state.anchorPosition?.let { position ->
            val closestPage = state.closestPageToPosition(position)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }

    private suspend fun fetchRemoteIfNecessary() {
        if (!localDataSource.hasCities()) {
            val rawCities = remoteLocalDataSource.getCitiesList()

            val resultList = if (rawCities.isSuccessful) {
                rawCities.body().orEmpty()
            } else {
                throw HttpException(rawCities)
            }

            localDataSource.insertCities(resultList)
        }
    }
}
