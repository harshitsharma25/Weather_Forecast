package com.example.jetweatherforecast.screens.settings

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.jetweatherforecast.model.Unit


@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel(){
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()



    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getUnits().distinctUntilChanged()
                .collect { listOfUnits ->
                    if(listOfUnits.isEmpty()) {
                        Log.d("TAG",":Empty List")
                    } else {
                        _unitList.value = listOfUnits
                    }
                }
        }
    }


    // Call this function whenever you make an API request

    fun insertUnit(unit:Unit) = viewModelScope.launch { repository.insertUnit(unit) }
    fun updateUnit(unit:Unit) = viewModelScope.launch { repository.updateUnit(unit) }
    fun deleteUnit(unit:Unit) = viewModelScope.launch { repository.deleteUnit(unit) }
    fun deleteAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }


}