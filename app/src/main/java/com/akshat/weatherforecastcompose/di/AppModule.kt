package com.akshat.weatherforecastcompose.di

import android.content.Context
import androidx.room.Room
import com.akshat.weatherforecastcompose.data.WeatherDao
import com.akshat.weatherforecastcompose.data.WeatherDataBase
import com.akshat.weatherforecastcompose.network.WeatherApi
import com.akshat.weatherforecastcompose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDataBase: WeatherDataBase): WeatherDao =
        weatherDataBase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDataBase =
        Room.databaseBuilder(
            context,
            WeatherDataBase::class.java,
            "weather_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherApi::class.java)
    }

}