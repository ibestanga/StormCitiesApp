package com.ibra.dev.stormcitiesapp.home.presentation.screens

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
    val viewModel = koinViewModel<HomeViewModel>()
    val cities = viewModel.pagingDataStateFlow.collectAsLazyPagingItems()

    LaunchedEffect(null) {
        viewModel.getCitiesList()
    }

    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(padding_8dp),
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(padding_24dp))
                SearchBar(onOnlyFavorite = { onlyFavorite ->
                    viewModel.getCitiesList(onlyFavorite)
                }
                ) { query, onlyFavorite ->
                    viewModel.filterByName(query, onlyFavorite)
                }
                Spacer(Modifier.height(padding_16dp))
            }
        }
    ) { paddingValues ->

        HandlerPageStates(
            cities = cities,
            showLoading = {
                ShowLoading(Modifier.padding(paddingValues))
            },
            showErrorState = {
                ShowResultMessage(
                    Modifier.padding(paddingValues),
                    msg = stringResource(R.string.home_generic_error_msg)
                )
            },
            showEmptyList = {
                ShowResultMessage(
                    Modifier.padding(paddingValues),
                    msg = stringResource(R.string.home_not_found_element)
                )
            }
        ) {
            ShowCitiesList(
                modifier = Modifier.padding(paddingValues),
                cities = cities,
                onNavigateLocationClick = { cityId ->
                    navController.navigate(LocationScreenDestination(cityId))
                }
            ) { id, isFavorite ->
                viewModel.setCityLikeFavorite(id, isFavorite)
            }
        }
    }
}

@Composable
private fun HandlerPageStates(
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