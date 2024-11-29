package com.ibra.dev.stormcitiesapp

import android.app.Application
import com.ibra.dev.stormcitiesapp.commons.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(coreModule))
        }
    }
}