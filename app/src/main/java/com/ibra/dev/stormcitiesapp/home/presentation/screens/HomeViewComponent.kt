package com.ibra.dev.stormcitiesapp.home.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.compose.LazyPagingItems
import com.ibra.dev.stormcitiesapp.commons.ui.theme.mediumPadding
import com.ibra.dev.stormcitiesapp.commons.ui.theme.singlePadding
import com.ibra.dev.stormcitiesapp.commons.ui.theme.smallCornerRadius
import com.ibra.dev.stormcitiesapp.commons.ui.theme.smallPadding
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto


@Composable
fun ShowLoading(modifier: Modifier) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowErrorMessage(modifier: Modifier) {
    Box(
        modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Ha ocurrido un error")
    }
}

@Composable
fun ShowEmptyMessage(modifier: Modifier) {
    Box(
        modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No se ha encontrado elementos ")
    }
}

@Composable
fun ShowCitiesList(
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
fun SearchBar(
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "${city.name}, ${city.country}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Example Icon",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}