package com.ibra.dev.stormcitiesapp.home.data.datasource.local

import androidx.paging.PagingSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

interface HomeLocalDataSource {

    fun getPagedCities(): PagingSource<Int, CityEntity>

    fun getCitiesByName(name:String) : PagingSource<Int, CityEntity>

    suspend fun insertSortedCities(sortedCities: List<CityEntity>)

    suspend fun hasCities(): Boolean
}