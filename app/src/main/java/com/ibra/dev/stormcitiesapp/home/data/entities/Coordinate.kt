package com.ibra.dev.stormcitiesapp.home.data.entities


import com.google.gson.annotations.SerializedName

data class Coordinate(
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val lon: Double? = null
)