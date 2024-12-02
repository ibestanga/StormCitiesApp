package com.ibra.dev.stormcitiesapp.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto
import com.ibra.dev.stormcitiesapp.home.presentation.usecase.GetCitiesPagedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCitiesUseCase: GetCitiesPagedUseCase
) : ViewModel() {

    private val _pagingDataStateFlow = MutableStateFlow<PagingData<CityDto>>(PagingData.empty())
    val pagingDataStateFlow: StateFlow<PagingData<CityDto>> = _pagingDataStateFlow

    fun getCitiesList() {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.invoke()
                .cachedIn(viewModelScope)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = PagingData.empty()
                ).collect {
                    _pagingDataStateFlow.value = it
                }
        }
    }

    fun filterByName(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.invoke(query)
                .cachedIn(viewModelScope)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = PagingData.empty()
                ).collect {
                    _pagingDataStateFlow.value = it
                }
        }
    }
}