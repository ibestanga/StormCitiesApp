package com.ibra.dev.stormcitiesapp.home.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityDto(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?
)