package com.ibra.dev.stormcitiesapp.commons.database

import androidx.room.TypeConverter
import com.ibra.dev.stormcitiesapp.home.data.entities.Coordinate

class Converters {
    @TypeConverter
    fun fromCoordinate(coordinate: Coordinate?): String? {
        return coordinate?.let { "${it.lat},${it.lon}" }
    }

    @TypeConverter
    fun toCoordinate(coordinateString: String?): Coordinate? {
        return coordinateString?.let {
            val (lat, lon) = it.split(",")
            Coordinate(lat.toDouble(), lon.toDouble())
        }
    }
}