package com.amadeus.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amadeus.myapplication.models.WeatherDataItem

@Database(entities = [WeatherDataItem::class], version = 3)
@TypeConverters(Converters::class)
@SuppressWarnings
abstract class WeatherForecastDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherForecastDao
}