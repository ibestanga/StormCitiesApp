package com.ibra.dev.stormcitiesapp.home.data.datasource.local

import androidx.paging.PagingSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

interface HomeLocalDataSource {

    suspend fun getPagedCities(limit: Int, offset: Int): List<CityEntity>

    fun getCitiesByName(name: String): PagingSource<Int, CityEntity>

    fun getOnlyFavoriteCities(): PagingSource<Int, CityEntity>

    suspend fun insertCities(sortedCities: List<CityEntity>)

    suspend fun hasCities(): Boolean

    suspend fun setFavoriteState(cityId: Int, favorite: Boolean)
}