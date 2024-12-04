package com.ibra.dev.stormcitiesapp.home.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.data.entities.Coordinate
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetCitiesPagedUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCitiesPagedUseCaseImplTest {

    private fun repositoryProvider(): HomeRepository = mockk(relaxed = true)

    private fun sutProvider(repository: HomeRepository): GetCitiesPagedUseCase =
        GetCitiesPagedUseCaseImpl(repository)

    @Test
    fun `invoke with onlyFavorite returns PagingData of cities`() = runTest {
        val repository = repositoryProvider()
        val sut = sutProvider(repository)
        val cityId = 1
        val nameCity = "City1"
        val isFavorite = true
        val lat = 2.0
        val long = 2.0
        val coordinate = Coordinate(
            lat = lat,
            lon = long
        )

        val mockCityEntity = CityEntity(
            id = cityId,
            name = nameCity,
            isFavorite = isFavorite,
            coordinate = coordinate
        )
        val mockPagingData = PagingData.from(listOf(mockCityEntity))

        every { repository.getCitiesPage(true) } returns flowOf(mockPagingData)

        sut.invoke(onlyFavorite = true).first().map { resultList ->
            assertEquals(nameCity, resultList.name)
            assertEquals(cityId, resultList.id)
            assertEquals(isFavorite, resultList.isFavorite)
            assertEquals(lat, resultList.latitude)
            assertEquals(lat, resultList.longitude)
        }

    }

}