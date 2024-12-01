package com.ibra.dev.stormcitiesapp.home.domain.repositories

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun fetchCities(): Flow<PagingData<CityEntity>>

    fun getCitiesPage(): PagingSource<Int, CityEntity>

    suspend fun filterByName(nameCity: String): PagingSource<Int, CityEntity>
}