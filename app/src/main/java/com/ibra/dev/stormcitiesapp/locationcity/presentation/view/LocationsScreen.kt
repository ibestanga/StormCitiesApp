package com.ibra.dev.stormcitiesapp.locationcity.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ibra.dev.stormcitiesapp.commons.presentation.views.MyTopBar
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto

@Composable
fun MapsScreen(
    navController: NavController,
    cityId: Int
) {
    Scaffold(
        topBar = {
            MyTopBar(title = "Location", needBackNavigation = true, onBackPressClick = {})
        }
    ) {
        MyMap(Modifier.padding(it), CityDto())
    }
}

@Composable
fun MyMap(modifier: Modifier, city: CityDto) {
    val latLng = LatLng(
        city.latitude,
        city.longitude
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 8f)
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = latLng),
            title = countryName,
            snippet = "Pais de origen: $countryName"
        )
    }
}