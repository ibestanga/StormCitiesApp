package com.ibra.dev.stormcitiesapp.home.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibra.dev.stormcitiesapp.commons.ui.theme.mediumPadding
import com.ibra.dev.stormcitiesapp.commons.ui.theme.singlePadding
import com.ibra.dev.stormcitiesapp.commons.ui.theme.smallCornerRadius
import com.ibra.dev.stormcitiesapp.commons.ui.theme.smallPadding
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CityListScreen(modifier: Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()
    val cities = viewModel.pagingDataStateFlow.collectAsLazyPagingItems()
    val isLoading by viewModel.isLoginStateFlow.collectAsState()

    LaunchedEffect(null) {
        viewModel.getCitiesList()
    }

    if (isLoading) {
        ShowLoading(modifier)
    } else {
        ShowCitiesList(modifier, viewModel, cities)
    }
}

@Composable
private fun ShowLoading(modifier: Modifier) {
    Box(
        modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ShowCitiesList(
    modifier: Modifier,
    viewModel: HomeViewModel,
    cities: LazyPagingItems<CityDto>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = modifier.height(mediumPadding))
        SearchBar { input ->
            if (input.isEmpty()) {
                viewModel.getCitiesList()
            } else {
                viewModel.filterByName(input)
            }
        }
        BodyList(modifier, cities)
    }
}

@Composable
private fun BodyList(
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
                CityItem(
                    Modifier
                        .fillMaxWidth()
                        .padding(singlePadding),
                    city
                )
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
fun CityItem(modifier: Modifier, city: CityDto) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(smallCornerRadius),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = smallPadding
        )
    ) {
        Box(
            modifier = Modifier
                .padding(mediumPadding),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "${city.name}, ${city.country}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}