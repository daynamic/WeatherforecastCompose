package com.akshat.weatherforecastcompose.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akshat.weatherforecastcompose.data.DataOrException
import com.akshat.weatherforecastcompose.model.Weather
import com.akshat.weatherforecastcompose.widgets.WeatherAppBar


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = "Toronto")
    }.value

    if (weatherData.loading == true)
        CircularProgressIndicator()
    else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold (topBar = {
        WeatherAppBar(title = weather.city.name + ", ${weather.city.country}",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            elevation = 7.dp){

        }

    }) { _ ->
        MainContent(data = weather)
    }


}

@Composable
fun MainContent(data: Weather) {
    Text(modifier = Modifier.padding(
        top = 9.dp,
        bottom = 8.dp
    ),text = data.city.name)

}
