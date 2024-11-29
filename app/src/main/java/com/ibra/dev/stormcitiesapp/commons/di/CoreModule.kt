package com.ibra.dev.stormcitiesapp.commons.di

import com.ibra.dev.stormcitiesapp.home.di.HomeModule
import org.koin.dsl.module

val coreModule = module {
    includes(
        networkModule,
        HomeModule
    )
}