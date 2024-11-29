package com.ibra.dev.stormcitiesapp.home.data.api

import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    companion object {
        const val CITIES_PATH =
            "hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json"
    }

    @GET(CITIES_PATH)
    suspend fun getCitiesList(): Response<List<CityEntity>>
}