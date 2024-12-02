package com.ibra.dev.stormcitiesapp.home.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibra.dev.stormcitiesapp.R
import com.ibra.dev.stormcitiesapp.commons.presentation.views.MyMap
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.viewmodels.HomeViewModel

@Composable
fun HomeLandscapeLayout(
    viewModel: HomeViewModel,
) {
    val cities = viewModel.pagingDataStateFlow.collectAsLazyPagingItems()

    var lastCityPressed: CityDto? by remember { mutableStateOf(null) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = LocalConfiguration.current.screenWidthDp.dp * 0.03f,
                bottom = LocalConfiguration.current.screenWidthDp.dp * 0.02f,
                start = LocalConfiguration.current.screenWidthDp.dp * 0.05f,
                end = LocalConfiguration.current.screenWidthDp.dp * 0.05f
            )
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            onOnlyFavorite = { onlyFavorite ->
                viewModel.getCitiesList(onlyFavorite)
            }
        ) { query, onlyFavorite ->
            viewModel.filterByName(query, onlyFavorite)
        }

        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(0.5f)) {
                HandlerPageStates(
                    cities = cities,
                    showLoading = {
                        ShowLoading(Modifier)
                    },
                    showErrorState = {
                        ShowResultMessage(
                            Modifier,
                            msg = stringResource(R.string.home_generic_error_msg)
                        )
                    },
                    showEmptyList = {
                        ShowResultMessage(
                            Modifier,
                            msg = stringResource(R.string.home_not_found_element)
                        )
                    }
                ) {
                    ShowCitiesList(
                        modifier = Modifier,
                        cities = cities,
                        onNavigateLocationClick = { city ->
                            lastCityPressed = city
                        }
                    ) { id, isFavorite ->
                        viewModel.setCityLikeFavorite(id, isFavorite)
                    }
                }
            }
            Box(modifier = Modifier.weight(0.5f)) {
                lastCityPressed?.let { city ->
                    MyMap(modifier = Modifier, city, zoom = 10F)
                } ?: ShowResultMessage(
                    Modifier,
                    msg = "Debe seleccionar una ciudad"
                )
            }
        }
    }

    }

}
