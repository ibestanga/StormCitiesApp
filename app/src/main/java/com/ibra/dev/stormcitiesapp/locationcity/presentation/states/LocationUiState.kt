package com.ibra.dev.stormcitiesapp.locationcity.presentation.states

import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto

data class LocationUiState(
    val city: CityDto? = null,
    val isLoading: Boolean = false
)
