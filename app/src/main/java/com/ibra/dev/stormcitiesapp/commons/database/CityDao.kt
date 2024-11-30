package com.ibra.dev.stormcitiesapp.commons.database

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSortedCities(cities: List<CityEntity>)

    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getPagedCities(): PagingSource<Int, CityEntity>

    @Query("SELECT * FROM cities WHERE id = :id")
    suspend fun getCityById(id: Int): CityEntity

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun getCityCount(): Int

    @Query("SELECT 1 FROM cities LIMIT 1")
    suspend fun hasAnyCity(): Boolean
}