package com.ibra.dev.stormcitiesapp.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.room.Query
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetCitiesPagedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCitiesUseCase: GetCitiesPagedUseCase
) : ViewModel() {

    val pagingDataStateFlow = MutableSharedFlow<PagingData<CityDto>>()

    private val _isLoginStateFlow = MutableStateFlow(false)
    val isLoginStateFlow: StateFlow<Boolean> get() = _isLoginStateFlow

    fun getCitiesList() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoginStateFlow.value = true
            getCitiesUseCase.invoke().collect {
                _isLoginStateFlow.value = false
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