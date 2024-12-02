package com.ibra.dev.stormcitiesapp.home.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibra.dev.stormcitiesapp.R
import com.ibra.dev.stormcitiesapp.commons.presentation.navigation.LocationScreenDestination
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_24dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_16dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_8dp
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val viewModel = koinViewModel<HomeViewModel>()


    LaunchedEffect(null) {
        viewModel.getCitiesList()
    }

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> HomePortraitLayout(
            navController = navController,
            viewModel = viewModel
        )

        Configuration.ORIENTATION_LANDSCAPE -> HomeLandscapeLayout(
            viewModel = viewModel
        )

        else -> HomePortraitLayout(
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
fun HandlerPageStates(
    cities: LazyPagingItems<CityDto>,
    showLoading: @Composable () -> Unit,
    showErrorState: @Composable () -> Unit,
    showEmptyList: @Composable () -> Unit,
    showCitiesList: @Composable () -> Unit,
) {
    val loadingState: Boolean =
        cities.loadState.refresh is LoadState.Loading && cities.itemCount == 0
    val emptyState: Boolean =
        cities.loadState.refresh is LoadState.NotLoading && cities.itemCount == 0
    val errorState: Boolean = cities.loadState.hasError
    when {
        loadingState -> showLoading()
        emptyState -> showEmptyList()
        errorState -> showErrorState()
        else -> showCitiesList()
    }
}