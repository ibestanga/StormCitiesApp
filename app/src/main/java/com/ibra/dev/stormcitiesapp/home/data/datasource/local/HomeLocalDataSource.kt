package com.ibra.dev.stormcitiesapp.home.data.datasource.local

import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

interface HomeLocalDataSource {

   suspend fun getAllCities(): List<CityEntity>

   suspend fun insertSortedCities(sortedCities: List<CityEntity>)
}