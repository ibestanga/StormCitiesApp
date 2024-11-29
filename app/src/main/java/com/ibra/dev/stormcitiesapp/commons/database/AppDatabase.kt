package com.ibra.dev.stormcitiesapp.commons.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

@Database(entities = [CityEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}