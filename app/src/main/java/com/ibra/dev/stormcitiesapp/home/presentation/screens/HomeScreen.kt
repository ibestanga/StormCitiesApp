package com.ibra.dev.stormcitiesapp.home.presentation.screens

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibra.dev.stormcitiesapp.commons.presentation.navigation.LocationScreenDestination
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val viewModel = koinViewModel<HomeViewModel>()
    val cities = viewModel.pagingDataStateFlow.collectAsLazyPagingItems()

    LaunchedEffect(null) {
        viewModel.getCitiesList()
    }

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> HomePortraitLayout(
            onOnlyFavorite = { onlyFavorite ->
                viewModel.getCitiesList(onlyFavorite)
            },
            onSearchUser = { query, onlyFavorite ->
                viewModel.filterByName(query, onlyFavorite)
            },
            onNavigateLocationClick = { city ->
                navController.navigate(LocationScreenDestination(city.id))
            },
            onClickFavoriteIcon = { id, isFavorite ->
                viewModel.setCityLikeFavorite(id, isFavorite)
            },
            cities
        )

        Configuration.ORIENTATION_LANDSCAPE -> HomeLandscapeLayout(
            cities = cities,
            onOnlyFavorite = { onlyFavorite ->
                viewModel.getCitiesList(onlyFavorite)
            },
            onSearchUser = { query, onlyFavorite ->
                viewModel.filterByName(query, onlyFavorite)
            },
            onClickFavoriteIcon = { id, isFavorite ->
                viewModel.setCityLikeFavorite(id, isFavorite)
            }
        )

        else -> HomePortraitLayout(
            onOnlyFavorite = { onlyFavorite ->
                viewModel.getCitiesList(onlyFavorite)
            },
            onSearchUser = { query, onlyFavorite ->
                viewModel.filterByName(query, onlyFavorite)
            },
            onNavigateLocationClick = { city ->
                navController.navigate(LocationScreenDestination(city.id))
            },
            onClickFavoriteIcon = { id, isFavorite ->
                viewModel.setCityLikeFavorite(id, isFavorite)
            },
            cities
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