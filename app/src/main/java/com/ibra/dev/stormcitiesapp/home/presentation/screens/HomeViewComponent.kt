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
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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
    cities: LazyPagingItems<CityDto>,
    onClickFavoriteIcon: (Int, Boolean) -> Unit

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
                    city,
                    onClickFavoriteIcon
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    onSearchUser: (String) -> Unit,
) {
    var userDniText by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(false) }

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
            singleLine = true,
            placeholder = {
                Text(text = "escribe el nombre de la ciudad")
            },
        )
    }
}

@Composable
fun OnlyFavoriteChip(selected: Boolean, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text("Solo favoritos") },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
            labelColor = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun CityItem(
    modifier: Modifier,
    city: CityDto,
    onClickFavoriteIcon: (Int, Boolean) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(smallCornerRadius),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = smallPadding
        )
    ) {

        var isFavorite by remember { mutableStateOf(city.isFavorite) }

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
                        onClickFavoriteIcon(city.id, !isFavorite)
                        isFavorite = !isFavorite
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Example Icon",
                        tint = if (isFavorite) Color.Yellow else Color.Black
                    )
                }
            }
        }
    }
}