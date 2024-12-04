package com.ibra.dev.stormcitiesapp.commons.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)


    @Query("SELECT * FROM cities ORDER BY CASE WHEN name LIKE '[0-9]%' OR name LIKE '[!@#\$%^&*()_+={}|:\"<>?`~;,.]' THEN 1 ELSE 0 END, name ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedCities(limit: Int, offset: Int): List<CityEntity>

    @Query("SELECT * FROM cities WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun getCitiesByName(query: String): PagingSource<Int, CityEntity>

    @Query("SELECT * FROM cities WHERE name LIKE '%' || :query || '%' AND isFavorite = 1 ORDER BY name ASC")
    fun getOnlyFavoriteCitiesByName(query: String): PagingSource<Int, CityEntity>

    @Query("SELECT * FROM cities WHERE isFavorite = 1")
    fun getOnlyFavoriteCities(): PagingSource<Int, CityEntity>

    @Query("SELECT * FROM cities WHERE id = :id")
    suspend fun getCityById(id: Int): CityEntity

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun getCityCount(): Int

    @Query("UPDATE cities SET isFavorite = :isFavorite WHERE id = :cityId")
    suspend fun setFavoriteState(cityId: Int, isFavorite: Boolean)
}