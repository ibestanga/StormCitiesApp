package com.ibra.dev.stormcitiesapp.home.data.repositories

import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

interface HomeRepository {

    suspend fun getCitiesList() : List<CityEntity>
}