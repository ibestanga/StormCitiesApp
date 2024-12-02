package com.ibra.dev.stormcitiesapp.home.data.datasource.local

import androidx.paging.PagingSource
import com.ibra.dev.stormcitiesapp.commons.database.CityDao
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

class HomeLocalDataSourceImpl(
    private val dao: CityDao
) : HomeLocalDataSource {
    override suspend fun getPagedCities(limit: Int, offset: Int): List<CityEntity> {
        return dao.getPagedCities(limit, offset)
    }

    override fun getCitiesByName(name: String): PagingSource<Int, CityEntity> {
        return dao.getCitiesByName(name)
    }

    override suspend fun insertSortedCities(sortedCities: List<CityEntity>) {
        dao.insertSortedCities(sortedCities)
    }

    override suspend fun hasCities(): Boolean = dao.getCityCount() > 0

    override suspend fun setFavoriteState(cityId: Int, favorite: Boolean) {
        dao.setFavoriteState(cityId = cityId, isFavorite = favorite)
    }
}