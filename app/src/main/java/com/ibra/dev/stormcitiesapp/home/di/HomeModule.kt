package com.ibra.dev.stormcitiesapp.home.di

import com.ibra.dev.stormcitiesapp.home.data.api.HomeApi
import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.local.HomeLocalDataSourceImpl
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSource
import com.ibra.dev.stormcitiesapp.home.data.datasource.remote.HomeRemoteDataSourceImpl
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import com.ibra.dev.stormcitiesapp.home.data.repositories.HomeRepositoryImpl
import com.ibra.dev.stormcitiesapp.home.domain.usecases.GetCitiesPagedUseCaseImpl
import com.ibra.dev.stormcitiesapp.home.domain.usecases.SetCityFavoriteStateUseCaseImpl
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetCitiesPagedUseCase
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.SetCityFavoriteStateUseCase
import com.ibra.dev.stormcitiesapp.home.presentation.viewmodels.HomeViewModel
import org.koin.dsl.module
import retrofit2.Retrofit


private val dataModule = module {
    single { providesHomeApi(get()) }
    single<HomeRemoteDataSource> { HomeRemoteDataSourceImpl(get()) }
    single<HomeLocalDataSource> { HomeLocalDataSourceImpl(get()) }
    single<HomeRepository> {
        HomeRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}

private val domainModule = module {
    single<GetCitiesPagedUseCase> {
        GetCitiesPagedUseCaseImpl(get())
    }
    single<SetCityFavoriteStateUseCase> {
        SetCityFavoriteStateUseCaseImpl(get())
    }
}

private val presentationModule = module {
    single {
        HomeViewModel(getCitiesUseCase = get(), setCityFavoriteStateUseCase = get())
    }
}

val HomeModule = module {
    includes(
        dataModule,
        domainModule,
        presentationModule
    )
}

private fun providesHomeApi(retrofit: Retrofit): HomeApi {
    return retrofit.create(HomeApi::class.java)
}
