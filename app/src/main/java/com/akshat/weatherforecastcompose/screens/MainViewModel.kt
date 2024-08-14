package com.akshat.weatherforecastcompose.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshat.weatherforecastcompose.data.DataOrException
import com.akshat.weatherforecastcompose.model.Weather
import com.akshat.weatherforecastcompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel(){

    val data : MutableState<DataOrException<Weather, Boolean, Exception>>
    = mutableStateOf(DataOrException(null, true, Exception("")))

    suspend fun getWeatherData(city: String)
    : DataOrException<Weather, Boolean, Exception>{
        return repository.getWeather(cityQuery = city)
    }



}