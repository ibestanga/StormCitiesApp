package com.ibra.dev.stormcitiesapp.home.domain.repositories

import androidx.paging.PagingData
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun fetchCities(): Flow<PagingData<CityEntity>>

    suspend fun filterByName(nameCity: String): Flow<PagingData<CityEntity>>
}