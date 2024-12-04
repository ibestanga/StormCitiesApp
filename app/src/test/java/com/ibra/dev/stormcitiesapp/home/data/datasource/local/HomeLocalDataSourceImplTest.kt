package com.ibra.dev.stormcitiesapp.home.data.datasource.local

import com.ibra.dev.stormcitiesapp.commons.database.CityDao
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HomeLocalDataSourceImplTest {

    private fun daoProvider(): CityDao = mockk(relaxed = true)

    private fun sutProvider(dao: CityDao): HomeLocalDataSource = HomeLocalDataSourceImpl(dao)

    @Test
    fun `when call getPagedCities should call dao getPagedCities`() = runTest {
        //given
        val dao = daoProvider()
        val sut = sutProvider(dao)
        val limit = 20
        val outset = 5

        //when
        sut.getPagedCities(limit, outset)

        //them
        coVerify { dao.getPagedCities(any(), any()) }
    }

    @Test
    fun `when call getCitiesByName with only favorite false should call getCitiesByName`() =
        runTest {
            //given
            val dao = daoProvider()
            val sut = sutProvider(dao)
            val query = "name"
            val onlyFavorite = false

            //when
            sut.getCitiesByName(query, onlyFavorite)

            //them
            coVerify { dao.getCitiesByName(any()) }
        }

    @Test
    fun `when call getCitiesByName with only favorite true should call getOnlyFavoriteCitiesByName`() {
        //given
        val dao = daoProvider()
        val sut = sutProvider(dao)
        val query = "name"
        val onlyFavorite = true

        //when
        sut.getCitiesByName(query, onlyFavorite)

        //them
        verify { dao.getOnlyFavoriteCitiesByName(any()) }
    }

    @Test
    fun `when call getOnlyFavoriteCities should call dao getOnlyFavoriteCities`() {
        //given
        val dao = daoProvider()
        val sut = sutProvider(dao)

        //when
        sut.getOnlyFavoriteCities()

        //them
        coVerify { dao.getOnlyFavoriteCities() }
    }

    @Test
    fun `when call insertCities should call dao insertCities`() = runTest {
        //given
        val dao = daoProvider()
        val sut = sutProvider(dao)
        val cities = emptyList<CityEntity>()

        //when
        sut.insertCities(cities)

        //them
        coVerify { dao.insertCities(any()) }
    }

    @Test
    fun `when call hasCities and dao return major than zero then return true`() = runTest {
        //given
        val dao = daoProvider()
        val sut = sutProvider(dao)

        coEvery { dao.getCityCount() } returns 2

        //when
        val result = sut.hasCities()

        //then
        assertTrue(result)
    }

    @Test
    fun `when call hasCities and dao return zero then return false`() = runTest {
        //given
        val dao = daoProvider()
        val sut = sutProvider(dao)

        coEvery { dao.getCityCount() } returns 0

        //when
        val result = sut.hasCities()

        //then
        assertFalse(result)
    }

    @Test
    fun `when call setFavoriteState should call dao setFavoriteState`() = runTest {
        //given
        val dao = daoProvider()
        val sut = sutProvider(dao)
        val cityId = 1
        val favorite = true

        //when
        sut.setFavoriteState(cityId, favorite)

        //them
        coVerify { dao.setFavoriteState(any(), any()) }
    }
}