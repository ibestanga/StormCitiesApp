package com.ibra.dev.stormcitiesapp.commons.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ibra.dev.stormcitiesapp.commons.presentation.navigation.AppNav
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.StormCitiesAppTheme
import com.ibra.dev.stormcitiesapp.home.presentation.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StormCitiesAppTheme {
                    AppNav()
            }
        }
    }
}