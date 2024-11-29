package com.ibra.dev.stormcitiesapp.home.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.dev.stormcitiesapp.home.domain.repositories.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    fun getCitiesList() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(HomeViewModel::class.java.simpleName, "getCitiesList: start process")
            val result = repository.getCitiesList()
            Log.i(HomeViewModel::class.java.simpleName, "getCitiesList: result ${result.size}")
        }
    }
}