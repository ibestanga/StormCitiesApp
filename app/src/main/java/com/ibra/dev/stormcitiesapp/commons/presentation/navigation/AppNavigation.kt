package com.ibra.dev.stormcitiesapp.commons.presentation.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ibra.dev.stormcitiesapp.home.presentation.screens.HomeScreen
import com.ibra.dev.stormcitiesapp.locationcity.presentation.view.MapsScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = HomeScreenDestination) {
        composable<HomeScreenDestination> {
            HomeScreen(navController)
        }

        composable<LocationScreenDestination> { backStackEntry ->
            val cityId: Int = backStackEntry.toRoute<LocationScreenDestination>().cityId
            MapsScreen(navController = navController, cityId = cityId)
        }
    }
}