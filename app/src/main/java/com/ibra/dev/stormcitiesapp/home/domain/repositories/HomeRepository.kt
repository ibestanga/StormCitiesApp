package com.ibra.dev.stormcitiesapp.home.domain.repositories

import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto

interface HomeRepository {

    suspend fun getCitiesList() : List<CityEntity>
}