package com.ibra.dev.stormcitiesapp.commons.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreenDestination

@Serializable
data class LocationScreenDestination(val cityId: Int)