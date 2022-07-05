package com.amadeus.myapplication.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amadeus.myapplication.models.WeatherDataItem


@Dao
interface WeatherForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherDataItem: WeatherDataItem)

    @Query("SELECT * FROM weather_table ORDER BY country ASC")
    fun getAllWeather(): PagingSource<Int, WeatherDataItem>

    @Query("SELECT COUNT(*) FROM weather_table")
    fun getCount(): Int

    @Query("SELECT * FROM weather_table WHERE  name == :query")
    fun searchCity(query : String): PagingSource<Int, WeatherDataItem>
}