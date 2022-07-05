package com.amadeus.myapplication.repository

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
import com.amadeus.myapplication.db.WeatherForecastDao
import com.amadeus.myapplication.models.Weather
import com.amadeus.myapplication.models.WeatherDataItem
import kotlinx.coroutines.launch
import javax.inject.Inject


class WeatherForecastRepository @Inject constructor(private val weatherForecastDao: WeatherForecastDao) {

    fun getWeatherList(): PagingSource<Int, WeatherDataItem> {
        return weatherForecastDao.getAllWeather()
    }

    suspend fun saveWeatherDataItem(weatherDataItem: WeatherDataItem) {
        weatherForecastDao.insertWeather(weatherDataItem)
    }

    fun getCount(): Int {
        return weatherForecastDao.getCount()
    }

    fun getCitySearchResult(query: String): PagingSource<Int, WeatherDataItem> {
        return weatherForecastDao.searchCity(query)
    }


}