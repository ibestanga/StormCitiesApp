package com.ibra.dev.stormcitiesapp.home.di

import com.ibra.dev.stormcitiesapp.home.data.api.HomeApi
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.RemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.RemoteDataSourceImpl
import com.ibra.dev.stormcitiesapp.home.data.repositories.HomeRepository
import com.ibra.dev.stormcitiesapp.home.data.repositories.HomeRepositoryImpl
import com.ibra.dev.stormcitiesapp.home.presentation.viewmodels.HomeViewModel
import org.koin.dsl.module
import retrofit2.Retrofit


private val dataModule = module {
    single { providesHomeApi(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
}

private val presentationModule = module {
    single {
        HomeViewModel(get())
    }
}

val HomeModule = module {
    includes(
        dataModule,
        presentationModule
    )
}

private fun providesHomeApi(retrofit: Retrofit): HomeApi {
    return retrofit.create(HomeApi::class.java)
}
