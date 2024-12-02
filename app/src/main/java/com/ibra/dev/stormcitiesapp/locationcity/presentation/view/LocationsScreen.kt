package com.ibra.dev.stormcitiesapp.locationcity.presentation.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ibra.dev.stormcitiesapp.commons.presentation.views.MyMap
import com.ibra.dev.stormcitiesapp.commons.presentation.views.MyTopBar
import com.ibra.dev.stormcitiesapp.locationcity.presentation.viewmodels.LocationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MapsScreen(
    navController: NavController,
    cityId: Int
) {
    val viewModel = koinViewModel<LocationViewModel>()
    val city = viewModel.locationsUiStateFlow.collectAsState().value.city

    LaunchedEffect(null) {
        viewModel.getCityById(cityId)
    }

    DisposableEffect(null) {
        onDispose {
            viewModel.clearData()
        }
    }

    Scaffold(
        topBar = {
            MyTopBar(
                title = "Location",
                actionIcon = Icons.Filled.Info,
                needBackNavigation = true,
                onBackPressClick = {
                navController.popBackStack()
            },
            )
        }
    ) {
        city?.let { city -> MyMap(Modifier.padding(it), city) }
    }
}

