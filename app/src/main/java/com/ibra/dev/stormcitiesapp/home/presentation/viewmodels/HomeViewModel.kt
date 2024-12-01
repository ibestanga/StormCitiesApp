package com.ibra.dev.stormcitiesapp.home.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.room.Query
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetCitiesPagedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCitiesUseCase: GetCitiesPagedUseCase
) : ViewModel() {
    companion object {
        const val TAG = "HomeViewModel"
    }

    val pagingDataStateFlow = MutableSharedFlow<PagingData<CityDto>>()

    var citiesData: Flow<PagingData<CityDto>> =  getCitiesUseCase.invoke().cachedIn(viewModelScope)


    private val _isLoginStateFlow = MutableStateFlow(false)
    val isLoginStateFlow: StateFlow<Boolean> get() = _isLoginStateFlow

    fun getCitiesList() {
        viewModelScope.launch(Dispatchers.IO) {
           citiesData = getCitiesUseCase.invoke().cachedIn(viewModelScope)
        }
    }

    fun filterByName(query: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            getCitiesUseCase.invoke(query = query).collect {
//                pagingDataStateFlow.emit(it)
//            }
//        }
    }
}