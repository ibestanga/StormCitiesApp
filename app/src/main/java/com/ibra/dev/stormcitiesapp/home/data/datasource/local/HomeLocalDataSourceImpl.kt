package com.ibra.dev.stormcitiesapp.home.data.datasource.local

import androidx.paging.PagingSource
import com.ibra.dev.stormcitiesapp.commons.database.CityDao
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

class HomeLocalDataSourceImpl(
    private val dao: CityDao
): HomeLocalDataSource {
    override fun getPagedCities(): PagingSource<Int, CityEntity> {
        return dao.getPagedCities()
    }

    override suspend fun insertSortedCities(sortedCities: List<CityEntity>) {
       dao.insertSortedCities(sortedCities)
    }

    override suspend fun hasCities(): Boolean = dao.getCityCount() > 0
}