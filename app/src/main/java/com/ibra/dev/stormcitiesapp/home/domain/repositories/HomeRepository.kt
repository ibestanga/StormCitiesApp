package com.ibra.dev.stormcitiesapp.home.domain.repositories

import androidx.paging.PagingData
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getCitiesPage(onlyFavorite: Boolean): Flow<PagingData<CityEntity>>

    fun filterByName(nameCity: String): Flow<PagingData<CityEntity>>

    suspend fun setFavoriteState(cityId: Int, isFavorite: Boolean)
}