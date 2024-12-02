package com.ibra.dev.stormcitiesapp.commons.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ibra.dev.stormcitiesapp.commons.ui.theme.StormCitiesAppTheme
import com.ibra.dev.stormcitiesapp.home.presentation.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StormCitiesAppTheme {
                    HomeScreen()
            }
        }
    }
}