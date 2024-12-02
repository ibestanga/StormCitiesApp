package com.ibra.dev.stormcitiesapp.home.data.entities


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ibra.dev.stormcitiesapp.commons.extfunc.orZero
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto

@Entity(tableName = "cities", indices = [Index("name")])
data class CityEntity(
    @SerializedName("coord")
    val coordinate: Coordinate? = null,
    @SerializedName("country")
    val country: String? = null,
    @PrimaryKey
    @SerializedName("_id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    val isFavorite: Boolean = false
)

fun CityEntity.toDto() = CityDto(
    country = country,
    id = id.orZero(),
    name = name,
    longitude = coordinate?.lon,
    latitude = coordinate?.lat,
    isFavorite = isFavorite
)