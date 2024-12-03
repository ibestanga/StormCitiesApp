package com.ibra.dev.stormcitiesapp.home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.ibra.dev.stormcitiesapp.R
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_16dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_32dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_8dp
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto

@Composable
fun HomePortraitLayout(
    onOnlyFavorite: (Boolean) -> Unit,
    onSearchUser: (String, Boolean) -> Unit,
    onNavigateLocationClick: (CityDto) -> Unit,
    onClickFavoriteIcon: (Int, Boolean) -> Unit,
    cities: LazyPagingItems<CityDto>
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding_8dp),
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                Spacer(Modifier.height(padding_32dp))
                SearchBar(
                    onOnlyFavorite = { onlyFavorite ->
                        onOnlyFavorite(onlyFavorite)
                    }
                ) { query, onlyFavorite ->
                    onSearchUser(query, onlyFavorite)
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
                onNavigateLocationClick = { city ->
                    onNavigateLocationClick(city)
                }
            ) { id, isFavorite ->
                onClickFavoriteIcon(id, isFavorite)
            }
        }
    }
}