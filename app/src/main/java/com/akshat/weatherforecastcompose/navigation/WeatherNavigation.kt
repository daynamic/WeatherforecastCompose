package com.akshat.weatherforecastcompose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.akshat.weatherforecastcompose.screens.MainScreen
import com.akshat.weatherforecastcompose.screens.MainViewModel
import com.akshat.weatherforecastcompose.screens.WeatherSplashScreen
import com.akshat.weatherforecastcompose.screens.about.AboutScreen
import com.akshat.weatherforecastcompose.screens.favourites.FavouriteScreen
import com.akshat.weatherforecastcompose.screens.search.SearchScreen
import com.akshat.weatherforecastcompose.screens.setting.SettingScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city)
            }


        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingScreen(navController = navController)
        }

        composable(WeatherScreens.FavouriteScreen.name) {
            FavouriteScreen(navController = navController)
        }

    }
}