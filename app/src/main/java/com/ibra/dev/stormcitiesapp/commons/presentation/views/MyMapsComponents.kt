package com.ibra.dev.stormcitiesapp.commons.presentation.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto

@Composable
fun MyMap(
    modifier: Modifier,
    city: CityDto,
    zoom: Float = 14f
) {
    val latLng = LatLng(
        city.latitude,
        city.longitude
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, zoom)
    }

    LaunchedEffect(city.id) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(latLng, zoom)
        )
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = latLng),
            title = city.name,
            snippet = "Pais de origen: ${city.country}"
        )
    }
}