package com.ibra.dev.stormcitiesapp.locationcity.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.dev.stormcitiesapp.locationcity.presentation.states.LocationUiState
import com.ibra.dev.stormcitiesapp.locationcity.presentation.usecase.GetCityByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(
    private val getCityByIdUseCase: GetCityByIdUseCase
) : ViewModel() {

    private val _locationsUiStateFlow = MutableStateFlow(LocationUiState())
    val locationsUiStateFlow: StateFlow<LocationUiState> = _locationsUiStateFlow

    fun getCityById(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val city = getCityByIdUseCase.invoke(id)
            _locationsUiStateFlow.value = LocationUiState(
                city = city,
            )
        }
    }
}