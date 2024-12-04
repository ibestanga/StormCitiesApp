package com.ibra.dev.stormcitiesapp.home.data.repositories

import androidx.paging.PagingSource
import app.cash.turbine.test
import com.ibra.dev.stormcitiesapp.home.data.CityPagingSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HomeRepositoryImplTest {

    private fun localDataSourceProvider(): HomeLocalDataSource = mockk(relaxed = true)

    private fun remoteDataSourceProvider(): HomeRemoteDataSource = mockk(relaxed = true)

    private fun sutProvider(
        remoteDataSource: HomeRemoteDataSource,
        localDataSource: HomeLocalDataSource,
    ): HomeRepository = HomeRepositoryImpl(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource,
    )

    @Test
    fun `when call getCitiesPage with onlyFavorite like true should be call getOnlyFavoritesCities`() =
        runTest {
            //Given
            val localDataSource = localDataSourceProvider()
            val remoteDataSourceProvider = remoteDataSourceProvider()
            val sut = sutProvider(
                remoteDataSource = remoteDataSourceProvider,
                localDataSource = localDataSource,
            )
            val favoritePagingSource: PagingSource<Int, CityEntity> = mockk(relaxed = true)
            coEvery { localDataSource.getOnlyFavoriteCities() } returns favoritePagingSource

            val result = sut.getCitiesPage(onlyFavorite = true)

            //when
            result.test {
                val pagingData = awaitItem()
                assertNotNull(pagingData)
                cancelAndIgnoreRemainingEvents()
            }

            // then
            coVerify { localDataSource.getOnlyFavoriteCities() }
        }

    @Test
    fun `getCitiesPage should fetch remote and local cities when onlyFavorite is false`() =
        runTest {
            // Given
            val localDataSource = mockk<HomeLocalDataSource>(relaxed = true)
            val remoteDataSource = mockk<HomeRemoteDataSource>(relaxed = true)

            val sut = HomeRepositoryImpl(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
            )

            // Mock PagingSource
            val mockPagingSource = mockk<CityPagingSource>(relaxed = true)
            coEvery { localDataSource.getPagedCities(any(), any()) } returns listOf(
                CityEntity(name = "City1"),
                CityEntity(name = "City2")
            )

            every { localDataSource.getOnlyFavoriteCities() } returns mockPagingSource

            // When
            val result = sut.getCitiesPage(onlyFavorite = false)

            // Then
            result.test {
                val pagingData = awaitItem()
                assertNotNull(pagingData)
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `when call filterByName should call getCitiesByName with according to params`() = runTest {
        // Given
        val localDataSource = mockk<HomeLocalDataSource>(relaxed = true)
        val remoteDataSource = mockk<HomeRemoteDataSource>(relaxed = true)
        val sut = HomeRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
        )
        val expectedQuery = "query test"
        val favoriteState = false
        val favoritePagingSource: PagingSource<Int, CityEntity> = mockk(relaxed = true)
        coEvery { localDataSource.getCitiesByName(any(), any()) } returns favoritePagingSource


        // When
        val result = sut.filterByName(nameCity = expectedQuery, onlyFavorite = favoriteState)

        result.test {
            val pagingData = awaitItem()
            assertNotNull(pagingData)
            cancelAndIgnoreRemainingEvents()
        }

        // then
        verify {
            localDataSource.getCitiesByName(eq(expectedQuery), eq(favoriteState))
        }
    }

    @Test
    fun `when call setFavoriteState should call setFavoriteState with according to params`() =
        runTest {
            // Given
            val localDataSource = mockk<HomeLocalDataSource>(relaxed = true)
            val remoteDataSource = mockk<HomeRemoteDataSource>(relaxed = true)
            val sut = HomeRepositoryImpl(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
            )
            val expectedId = 1
            val favoriteState = true
            val favoritePagingSource: PagingSource<Int, CityEntity> = mockk(relaxed = true)
            coEvery { localDataSource.getCitiesByName(any(), any()) } returns favoritePagingSource

            // When
            sut.setFavoriteState(cityId = expectedId, isFavorite = favoriteState)

            // then
            coVerify {
                localDataSource.setFavoriteState(eq(expectedId), eq(favoriteState))
            }
        }
}