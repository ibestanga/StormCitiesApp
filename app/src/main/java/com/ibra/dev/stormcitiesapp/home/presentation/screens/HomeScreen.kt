package com.ibra.dev.stormcitiesapp.home.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibra.dev.stormcitiesapp.commons.ui.theme.mediumPadding
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

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = modifier.height(mediumPadding))
        SearchBar { input ->
            if (input.isEmpty()) {
                viewModel.getCitiesList()
            } else {
                viewModel.filterByName(input)
            }
        }
        CitiesLazyColum(modifier, cities)
    }

}

@Composable
private fun CitiesLazyColum(
    modifier: Modifier,
    cities: LazyPagingItems<CityDto>
) {
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
                loadState.append is LoadState.Loading -> {
                    Log.i("CityListScreen", "CityListScreen: Loading")
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item { ErrorItem(error.error.localizedMessage ?: "Error") }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    isError: Boolean = false,
    errorMessage: String? = null,
    onSearchUser: (String) -> Unit,
) {
    var userDniText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = mediumPadding,
                    end = mediumPadding,
                    top = mediumPadding,
                ),
            value = userDniText,
            onValueChange = {
                userDniText = it
                onSearchUser(it)
            },
            isError = isError,
            singleLine = true,
            placeholder = {
                Text(text = "escribe el nombre de la ciudad")
            },
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = mediumPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            errorMessage?.let {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = "Error",
                    tint = Color.Red
                )
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(mediumPadding)
                )
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