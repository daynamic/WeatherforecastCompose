package com.akshat.weatherforecastcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akshat.weatherforecastcompose.data.DataOrException
import com.akshat.weatherforecastcompose.model.Weather
import com.akshat.weatherforecastcompose.model.WeatherItem
import com.akshat.weatherforecastcompose.navigation.WeatherScreens
import com.akshat.weatherforecastcompose.utils.formatDate
import com.akshat.weatherforecastcompose.utils.formatDecimals
import com.akshat.weatherforecastcompose.widgets.HumidityWindPressureRow
import com.akshat.weatherforecastcompose.widgets.SunsetSunriseRow
import com.akshat.weatherforecastcompose.widgets.WeatherAppBar
import com.akshat.weatherforecastcompose.widgets.WeatherDetailRow
import com.akshat.weatherforecastcompose.widgets.WeatherStateImage


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = city.toString())
    }.value

    if (weatherData.loading == true)
        CircularProgressIndicator()
    else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + ", ${weather.city.country}",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            onAddActionClicked = {
                navController.navigate(route = WeatherScreens.SearchScreen.name)

            },
            elevation = 7.dp
        ) {

        }

    }) { it ->
        Surface(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            MainContent(data = weather)
        }
    }


}

@Composable
fun MainContent(data: Weather) {

    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

    Column(
        Modifier
            .padding(
                start = 4.dp,
                top = 9.dp,
                end = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate(weatherItem.dt), // Wed Nov 17
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherItem.temp.day) + "º",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)


            }

        }
        HumidityWindPressureRow(weather = data.list[0])
        Divider()
        SunsetSunriseRow(weather = data.list[0])
        Text(
            text = "This week",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.list) { item: WeatherItem ->
                    //     Text(item.temp.max.toString())
                    WeatherDetailRow(weather = item)

                }
            }

        }
    }


}


