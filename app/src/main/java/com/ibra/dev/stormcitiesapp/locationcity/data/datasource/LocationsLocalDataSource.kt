package com.ibra.dev.stormcitiesapp.locationcity.data.datasource

import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

interface LocationsLocalDataSource {

    suspend fun getCityById(id:Int): CityEntity
}