package com.ibra.dev.stormcitiesapp.home.data.entities


import com.google.gson.annotations.SerializedName

data class CityEntity(
    @SerializedName("coord")
    val coordinate: Coordinate? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("_id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)