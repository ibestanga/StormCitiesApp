package com.ibra.dev.stormcitiesapp.home.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ibra.dev.stormcitiesapp.home.data.CityPagingSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSourceImpl: HomeLocalDataSource
) : HomeRepository {
    companion object {
        const val TAG = "HomeRepositoryImpl"
        const val MAX_ITEMS = 10
        const val PREFETCH_ITEMS = 3
    }

    override fun getCitiesPage(): Flow<PagingData<CityEntity>> = executePagerFlow()

    private fun executePagerFlow() = Pager(
        config = PagingConfig(
            pageSize = MAX_ITEMS,
            enablePlaceholders = false,
            prefetchDistance = PREFETCH_ITEMS
        ),
        pagingSourceFactory = {
            CityPagingSource(
                localDataSourceImpl,
                remoteDataSource
            )
        }
    ).flow

    override fun filterByName(nameCity: String): Flow<PagingData<CityEntity>> = Pager(
        config = PagingConfig(
            pageSize = PREFETCH_ITEMS,
            enablePlaceholders = false,
            prefetchDistance = PREFETCH_ITEMS
        ),
        pagingSourceFactory = {
            localDataSourceImpl.getCitiesByName(nameCity)
        }
    ).flow

    override suspend fun setFavoriteState(cityId: Int, isFavorite: Boolean) {
        localDataSourceImpl.setFavoriteState(cityId, isFavorite)
    }
}
