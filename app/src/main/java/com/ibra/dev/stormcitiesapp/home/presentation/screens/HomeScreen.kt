package com.ibra.dev.stormcitiesapp.home.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CityListScreen(modifier: Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()
    val cities = viewModel.pagingDataStateFlow.collectAsLazyPagingItems()

    LaunchedEffect(null) {
        viewModel.getCitiesList()
    }

    LazyColumn(
        modifier
    ) {

        items(
            count = cities.itemCount,
            key = { index -> cities[index]?.id ?: index }
        ) { index ->
            cities[index]?.let { city ->
                CityItem(city)
            }
        }

        cities.apply {
            when {
                loadState.append is androidx.paging.LoadState.Loading -> {
                    Log.i("CityListScreen", "CityListScreen: Loading")
                    item { LoadingItem() }
                }

                loadState.refresh is androidx.paging.LoadState.Error -> {
                    val error = loadState.refresh as androidx.paging.LoadState.Error
                    item { ErrorItem(error.error.localizedMessage ?: "Error") }
                }
            }
        }
    }
}

@Composable
fun CityItem(city: CityDto) {
    BasicText(
        text = "${city.name}, ${city.country}",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun LoadingItem() {
    BasicText(text = "Cargando...")
}

@Composable
fun ErrorItem(message: String) {
    BasicText(text = "Error: $message")
}