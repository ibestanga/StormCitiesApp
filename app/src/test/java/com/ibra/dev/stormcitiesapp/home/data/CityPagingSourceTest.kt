package com.ibra.dev.stormcitiesapp.home.data

import androidx.paging.PagingSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException

class CityPagingSourceTest {

    private fun localDataSourceProvider(): HomeLocalDataSource = mockk(relaxed = true)

    private fun remoteDataSourceProvider(): HomeRemoteDataSource = mockk(relaxed = true)

    private fun sutProvider(
        remoteDataSource: HomeRemoteDataSource,
        localDataSource: HomeLocalDataSource,
    ) = CityPagingSource(
        localDataSource = localDataSource,
        remoteLocalDataSource = remoteDataSource
    )

    @Test
    fun `when call load and db have data should call getCitiesList from remote data source `() =
        runTest {

            val localDataSource = localDataSourceProvider()
            val remoteDataSource = remoteDataSourceProvider()
            val sut = sutProvider(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource
            )
            val params: PagingSource.LoadParams<Int> = paramsProvider()

            mockResponseHasCities(localDataSource, true)

            sut.load(params)

            coVerify { localDataSource.hasCities() }
            coVerify(exactly = 0) { remoteDataSource.getCitiesList() }
        }

    @Test
    fun `when call load and db haven't data and call remote data source but throw exception then return error`() =
        runTest {
            val localDataSource = localDataSourceProvider()
            val remoteDataSource = remoteDataSourceProvider()
            val sut = sutProvider(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource
            )
            val params: PagingSource.LoadParams<Int> = paramsProvider()
            mockResponseHasCities(localDataSource, false)
            coEvery { remoteDataSource.getCitiesList() } throws HttpException(mockk(relaxed = true))

            val result = sut.load(params)

            assertTrue(result is PagingSource.LoadResult.Error)
        }

    @Test
    fun `when call load and db haven't data should call getCitiesList from remote data source `() =
        runTest {
            val localDataSource = localDataSourceProvider()
            val remoteDataSource = remoteDataSourceProvider()
            val sut = sutProvider(
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource
            )
            val params: PagingSource.LoadParams<Int> = paramsProvider()

            mockResponseHasCities(localDataSource, false)

            sut.load(params)

            coVerify { localDataSource.hasCities() }
            coVerify { remoteDataSource.getCitiesList() }
        }

    private fun mockResponseHasCities(localDataSource: HomeLocalDataSource, response: Boolean) {
        coEvery { localDataSource.hasCities() } returns response
    }

    private fun paramsProvider(): PagingSource.LoadParams<Int> =
        mockk(relaxed = true) {
            every { key } returns 2
            every { loadSize } returns 2
        }

}