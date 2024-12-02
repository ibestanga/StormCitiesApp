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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibra.dev.stormcitiesapp.commons.ui.theme.padding_24dp
import com.ibra.dev.stormcitiesapp.commons.ui.theme.padding_16dp
import com.ibra.dev.stormcitiesapp.commons.ui.theme.padding_8dp
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
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
                ShowErrorMessage(Modifier.padding(paddingValues))
            },
            showEmptyList = {
                ShowEmptyMessage(Modifier.padding(paddingValues))
            }
        ) {
            ShowCitiesList(
                Modifier.padding(paddingValues),
                cities
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