package com.ibra.dev.stormcitiesapp.home.domain.models


data class CityDto(
    val id: Int,
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false
)