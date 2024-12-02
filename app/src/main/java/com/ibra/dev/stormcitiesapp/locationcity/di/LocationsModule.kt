package com.ibra.dev.stormcitiesapp.locationcity.di

import com.ibra.dev.stormcitiesapp.locationcity.data.datasource.LocationsLocalDataSource
import com.ibra.dev.stormcitiesapp.locationcity.data.datasource.LocationsLocalDataSourceImpl
import com.ibra.dev.stormcitiesapp.locationcity.data.repositories.LocationsRepositoryImpl
import com.ibra.dev.stormcitiesapp.locationcity.domain.repositories.LocationsRepository
import com.ibra.dev.stormcitiesapp.locationcity.domain.usecase.GetCityByIdUseCaseImpl
import com.ibra.dev.stormcitiesapp.locationcity.presentation.usecase.GetCityByIdUseCase
import com.ibra.dev.stormcitiesapp.locationcity.presentation.viewmodels.LocationViewModel
import org.koin.dsl.module

private val dataModule = module {
    single<LocationsLocalDataSource> {
        LocationsLocalDataSourceImpl(get())
    }
    single<LocationsRepository> {
        LocationsRepositoryImpl(get())
    }
}

private val domainModule = module {
    single<GetCityByIdUseCase> {
        GetCityByIdUseCaseImpl(get())
    }
}

private val presentationModule = module {
    single {
        LocationViewModel(get())
    }
}

val locationModule = module {
    includes(
        dataModule,
        domainModule,
        presentationModule
    )
}