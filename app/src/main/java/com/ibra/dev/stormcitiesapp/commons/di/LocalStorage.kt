package com.ibra.dev.stormcitiesapp.commons.di

import android.content.Context
import androidx.room.Room
import com.ibra.dev.stormcitiesapp.commons.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        getDatabase(get())
    }

    single {
        (get() as AppDatabase).cityDao()
    }
}

fun getDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "AppDatabase"
    )
        .fallbackToDestructiveMigration()
        .build()
}