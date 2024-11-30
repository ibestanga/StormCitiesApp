package com.ibra.dev.stormcitiesapp.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ibra.dev.stormcitiesapp.home.data.entities.CityEntity
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetAllCitiesPagedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCitiesUseCase: GetAllCitiesPagedUseCase
) : ViewModel() {

    val pagingDataStateFlow = MutableSharedFlow<PagingData<CityDto>>()

    fun getCitiesList() {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.invoke().collect {
                pagingDataStateFlow.emit(it)
            }
        }
    }
}