package com.ibra.dev.stormcitiesapp.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.room.Query
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetCitiesPagedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCitiesUseCase: GetCitiesPagedUseCase
) : ViewModel() {

    val pagingDataStateFlow = MutableSharedFlow<PagingData<CityDto>>()

    fun getCitiesList() {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.invoke().collect {
                pagingDataStateFlow.emit(it)
            }
        }
    }

    fun filterByName(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.invoke(query = query).collect {
                pagingDataStateFlow.emit(it)
            }
        }
    }
}