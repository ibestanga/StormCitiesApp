package com.ibra.dev.stormcitiesapp.commons.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ibra.dev.stormcitiesapp.commons.di.coreModule
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.data.entities.Coordinate
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class CityDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private fun databaseProvider(): AppDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    private fun sutProvider(database: AppDatabase) = database.cityDao()

    private fun citiesProvider() = listOf(
        CityEntity(
            id = 1,
            name = "Alabama",
            country = "US",
            isFavorite = false,
            coordinate = Coordinate(
                lat = 2.0,
                lon = 2.0
            )
        ),
        CityEntity(
            id = 4,
            name = "Arizona",
            country = "US",
            isFavorite = true,
            coordinate = Coordinate(
                lat = 2.0,
                lon = 2.0
            )
        ),
        CityEntity(
            id = 5,
            name = "Sydney",
            country = "AU",
            isFavorite = false,
            coordinate = Coordinate(
                lat = 2.0,
                lon = 2.0
            )
        ),
        CityEntity(
            id = 2,
            name = "Albuquerque",
            country = "US",
            isFavorite = true,
            coordinate = Coordinate(
                lat = 2.0,
                lon = 2.0
            )
        ),
        CityEntity(
            id = 3,
            name = "Anaheim",
            country = "US",
            isFavorite = false,
            coordinate = Coordinate(
                lat = 2.0,
                lon = 2.0
            )
        ),
    )

    @Before
    fun setupKoin() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(listOf(coreModule))  // Define your Koin modules here
            }
        }
    }

    @After
    fun tearDownKoin() {
        stopKoin()
    }

    @Test
    fun `when call call getPagedCities should return cities sorter by alphabetically`() = runTest {
        // given
        val citiesList = citiesProvider()
        val database = databaseProvider()
        val sut = sutProvider(database)
        sut.insertCities(citiesList)
        val expectedList = citiesList.sortedBy { it.name }

        //when
        val result = sut.getPagedCities(20, 0)

        //then
        assertTrue(result == expectedList)
        database.close()
    }

    @Test
    fun `when call getOnlyFavoriteCities should return cities with isFavorite like true`() =
        runTest {
            // given
            val citiesList = citiesProvider()
            val database = databaseProvider()
            val sut = sutProvider(database)
            sut.insertCities(citiesList)

            // when
            val result = sut.getOnlyFavoriteCities().let { pagingSource ->
                val config = pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false
                    )
                )
                (config as PagingSource.LoadResult.Page).data
            }

            // then
            assertEquals(2, result.size)
            assertTrue(result.all { it.isFavorite })
            database.close()
        }

    @Test
    fun `when call getCitiesByName with A all should return all cities except Sydney`() = runTest {
        // given
        val citiesList = citiesProvider()
        val database = databaseProvider()
        val sut = sutProvider(database)
        sut.insertCities(citiesList)
        val query = "a"

        // when
        val result = sut.getCitiesByName(query).let { pagingSource ->
            val config = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
            (config as PagingSource.LoadResult.Page).data
        }

        // then
        assertEquals(4, result.size)
        assertNull(result.find { it.name == "Sydney" })
        database.close()
    }

    @Test
    fun `when call getCitiesByName with AL all should return only Alabama and Albuquerque`() =
        runTest {
            // given
            val citiesList = citiesProvider()
            val database = databaseProvider()
            val sut = sutProvider(database)
            sut.insertCities(citiesList)
            val query = "AL"

            // when
            val result = sut.getCitiesByName(query).let { pagingSource ->
                val config = pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false
                    )
                )
                (config as PagingSource.LoadResult.Page).data
            }

            //then
            assertEquals(2, result.size)
            assertNotNull(result.find { it.name == "Alabama" })
            assertNotNull(result.find { it.name == "Albuquerque" })
            assertNull(result.find { it.name == "Anaheim" })
            assertNull(result.find { it.name == "Arizona" })
            assertNull(result.find { it.name == "Sydney" })
            database.close()
        }

    @Test
    fun `when call getCitiesByName with AL all should return only Albuquerque`() = runTest {
        // given
        val citiesList = citiesProvider()
        val database = databaseProvider()
        val sut = sutProvider(database)
        sut.insertCities(citiesList)
        val query = "ALB"

        // when
        val result = sut.getCitiesByName(query).let { pagingSource ->
            val config = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )
            (config as PagingSource.LoadResult.Page).data
        }

        //then
        assertEquals(1, result.size)
        assertNull(result.find { it.name == "Alabama" })
        assertNotNull(result.find { it.name == "Albuquerque" })
        assertNull(result.find { it.name == "Anaheim" })
        assertNull(result.find { it.name == "Arizona" })
        assertNull(result.find { it.name == "Sydney" })
        database.close()
    }

    @Test
    fun `when call getCitiesByName with A but only favorites should return only Arizona`() =
        runTest {
            // given
            val citiesList = citiesProvider()
            val database = databaseProvider()
            val sut = sutProvider(database)
            sut.insertCities(citiesList)
            val query = "A"

            // when
            val result = sut.getOnlyFavoriteCitiesByName(query).let { pagingSource ->
                val config = pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false
                    )
                )
                (config as PagingSource.LoadResult.Page).data
            }
            // then
            assertEquals(2, result.size)
            assertNull(result.find { it.name == "Alabama" })
            assertNotNull(result.find { it.name == "Albuquerque" })
            assertNull(result.find { it.name == "Anaheim" })
            assertNotNull(result.find { it.name == "Arizona" })
            assertNull(result.find { it.name == "Sydney" })
            database.close()
        }
}