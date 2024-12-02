package com.ibra.dev.stormcitiesapp.home.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.cornerRadius_12dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_16dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_1dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_4dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_8dp
import com.ibra.dev.stormcitiesapp.commons.presentation.views.StandardText
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
                        .padding(padding_8dp),
                    city,
                    onClickFavoriteIcon
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    onOnlyFavorite: (Boolean) -> Unit,
    onSearchUser: (String, Boolean) -> Unit,
) {
    var queryText by remember { mutableStateOf("") }
    var onlyFavorite by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = padding_16dp,
                    end = padding_16dp,
                    top = padding_16dp,
                ),
            value = queryText,
            onValueChange = { query ->
                queryText = query
                onSearchUser(query, onlyFavorite)
            },
            singleLine = true,
            placeholder = {
                Text(text = "escribe el nombre de la ciudad")
            },
        )

        OnlyFavoriteChip(onlyFavorite) {
            onlyFavorite = !onlyFavorite
            onOnlyFavorite(onlyFavorite)
        }
    }
}

@Composable
fun OnlyFavoriteChip(onlyFavorite: Boolean, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text("Solo favoritos") },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (onlyFavorite) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
            labelColor = if (onlyFavorite) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
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
        shape = RoundedCornerShape(cornerRadius_12dp),
        elevation = CardDefaults.cardElevation(defaultElevation = padding_4dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {

        var isFavorite by remember { mutableStateOf(city.isFavorite) }

        Box(
            modifier = Modifier
                .padding(padding_16dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {

                Column {
                    StandardText("${city.name}, ${city.country}")
                    Spacer(Modifier.height(padding_1dp))
                    StandardText(
                        "Lat: ${city.latitude}, Long${city.longitude}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                IconButton(
                    onClick = {
                        onClickFavoriteIcon(city.id, !isFavorite)
                        isFavorite = !isFavorite
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}