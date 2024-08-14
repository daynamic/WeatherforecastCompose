package com.akshat.weatherforecastcompose.repository

import android.util.Log
import com.akshat.weatherforecastcompose.data.DataOrException
import com.akshat.weatherforecastcompose.model.Weather
import com.akshat.weatherforecastcompose.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery)
        } catch (e: Exception) {
            Log.d("GET", "getWeatherError : $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather : $response")
        return DataOrException(data = response)
    }
}