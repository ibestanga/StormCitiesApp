package com.ibra.dev.stormcitiesapp.home.data.datasource.local

import androidx.paging.PagingSource
import com.ibra.dev.stormcitiesapp.commons.database.CityDao
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

class HomeLocalDataSourceImpl(
    private val dao: CityDao
) : HomeLocalDataSource {
    override suspend fun getPagedCities(limit: Int, offset: Int): List<CityEntity> =
        dao.getPagedCities(limit, offset)

    override fun getCitiesByName(name: String): PagingSource<Int, CityEntity> =
        dao.getCitiesByName(name)

    override fun getOnlyFavoriteCities(): PagingSource<Int, CityEntity> = dao.getOnLyFavoriteCity()

    override suspend fun insertCities(sortedCities: List<CityEntity>) {
        dao.insertCities(sortedCities)
    }

    override suspend fun hasCities(): Boolean = dao.getCityCount() > 0

    override suspend fun setFavoriteState(cityId: Int, favorite: Boolean) {
        dao.setFavoriteState(cityId = cityId, isFavorite = favorite)
    }
}