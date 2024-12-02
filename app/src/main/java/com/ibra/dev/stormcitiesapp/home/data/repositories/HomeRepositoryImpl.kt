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
    private val localDataSource: HomeLocalDataSource
) : HomeRepository {
    companion object {
        const val MAX_ITEMS = 10
        const val PREFETCH_ITEMS = 3
    }

    private val pagingConfig by lazy {
        PagingConfig(
            pageSize = MAX_ITEMS,
            enablePlaceholders = false,
            prefetchDistance = PREFETCH_ITEMS
        )
    }

    override fun getCitiesPage(onlyFavorite: Boolean): Flow<PagingData<CityEntity>> {
        return if (onlyFavorite) getOnlyFavoritesCities() else executePagerFlow()
    }

    private fun getOnlyFavoritesCities(): Flow<PagingData<CityEntity>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                localDataSource.getOnlyFavoriteCities()
            }
        ).flow
    }

    private fun executePagerFlow() = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            CityPagingSource(
                localDataSource,
                remoteDataSource
            )
        }
    ).flow

    override fun filterByName(nameCity: String, onlyFavorite: Boolean): Flow<PagingData<CityEntity>> = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            localDataSource.getCitiesByName(nameCity,onlyFavorite)
        }
    ).flow

    override suspend fun setFavoriteState(cityId: Int, isFavorite: Boolean) {
        localDataSource.setFavoriteState(cityId, isFavorite)
    }
}
