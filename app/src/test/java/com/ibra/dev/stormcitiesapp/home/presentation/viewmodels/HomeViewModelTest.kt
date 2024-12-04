package com.ibra.dev.stormcitiesapp.home.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetCitiesPagedUseCase
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.SetCityFavoriteStateUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var getCitiesUseCase: GetCitiesPagedUseCase

    @MockK
    private lateinit var setCityFavoriteStateUseCase: SetCityFavoriteStateUseCase

    private lateinit var viewModel: HomeViewModel

    private fun listDataProvider() = listOf(
        CityDto(1, "City1", "Country", latitude = 2.0, longitude = 2.0, isFavorite = true),
        CityDto(2, "City2", "Country", latitude = 2.0, longitude = 2.0, isFavorite = true)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = HomeViewModel(
            getCitiesUseCase,
            setCityFavoriteStateUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCitiesList should update pagingDataStateFlow`() = runTest {
        // given
        val listResult = mutableListOf<CityDto>()
        val expectedList = mutableListOf<CityDto>()
        val mockPagingData = PagingData.from(listDataProvider())
        coEvery {
            getCitiesUseCase.invoke(false)
        } returns flowOf(mockPagingData)

        // when
        viewModel.getCitiesList()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.pagingDataStateFlow.value
        result.map {
            listResult.add(it)
        }
        mockPagingData.map {
            expectedList.add(it)
        }

        // then
        assertEquals(expectedList, listResult)
        coVerify { getCitiesUseCase.invoke(false) }
    }

    @Test
    fun `filterByName should update pagingDataStateFlow with filtered cities`() = runTest {
        // given
        val listResult = mutableListOf<CityDto>()
        val expectedList = mutableListOf<CityDto>()
        val query = "City"
        val mockPagingData = PagingData.from(listDataProvider())
        coEvery {
            getCitiesUseCase.invoke(query, false)
        } returns flowOf(mockPagingData)

        // when
        viewModel.filterByName(query)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.pagingDataStateFlow.value
        result.map {
            listResult.add(it)
        }
        mockPagingData.map {
            expectedList.add(it)
        }

        // then
        assertEquals(expectedList, listResult)
        coVerify { getCitiesUseCase.invoke(query, false) }
    }

    @Test
    fun `setCityLikeFavorite should call use case with correct parameters`() = runTest {
        // then
        val cityId = 1
        val isFavorite = true
        coEvery {
            setCityFavoriteStateUseCase.invoke(cityId, isFavorite)
        } returns Unit

        // when
        viewModel.setCityLikeFavorite(cityId, isFavorite)
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        coVerify { setCityFavoriteStateUseCase.invoke(cityId, isFavorite) }
    }

    @Test
    fun `getCitiesList with onlyFavorite true should call use case with correct parameter`() =
        runTest {
            // give
            val mockPagingData = PagingData.from(
                listDataProvider()
            )
            coEvery {
                getCitiesUseCase.invoke(true)
            } returns flowOf(mockPagingData)

            // when
            viewModel.getCitiesList(onlyFavorite = true)
            testDispatcher.scheduler.advanceUntilIdle()

            // then
            coVerify { getCitiesUseCase.invoke(true) }
        }
}