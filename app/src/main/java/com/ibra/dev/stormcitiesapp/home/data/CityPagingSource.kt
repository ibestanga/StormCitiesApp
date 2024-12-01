package com.ibra.dev.stormcitiesapp.home.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ibra.dev.stormcitiesapp.commons.database.CityDao
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity

class CityPagingSource(
    private val dao: CityDao
) : PagingSource<Int, CityEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityEntity> {
        return try {
            val offset = params.key ?: 0 // Calcula el offset
            val limit = params.loadSize  // Usa el tamaño de página de PagingConfig

            // Llama al DAO para obtener los datos
            val cities = dao.getPagedCities(limit, offset)

            LoadResult.Page(
                data = cities,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (cities.isEmpty()) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CityEntity>): Int? {
        return state.anchorPosition?.let { position ->
            val closestPage = state.closestPageToPosition(position)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }
}
