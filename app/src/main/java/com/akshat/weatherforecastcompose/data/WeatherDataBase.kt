package com.akshat.weatherforecastcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akshat.weatherforecastcompose.model.Favourite
import com.akshat.weatherforecastcompose.model.Unit


@Database(entities = [Favourite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDataBase: RoomDatabase(){
    abstract fun weatherDao(): WeatherDao
}