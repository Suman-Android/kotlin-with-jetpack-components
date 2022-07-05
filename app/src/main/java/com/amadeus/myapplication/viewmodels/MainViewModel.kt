package com.amadeus.myapplication.viewmodels

import androidx.lifecycle.*
import androidx.paging.*
import com.amadeus.myapplication.models.WeatherDataItem
import com.amadeus.myapplication.repository.WeatherForecastRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherForecastRepository
) : ViewModel() {
    var currentQuery = ""
    private var pagingSource: PagingSource<Int, WeatherDataItem>? = null
        get() {
            if (field == null || field?.invalid == true) {
                if (currentQuery.isEmpty())
                    field = weatherRepository.getWeatherList()
                else
                    field = weatherRepository.getCitySearchResult(currentQuery)
            }
            return field
        }


    val newWeatherList = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            maxSize = 200
        )
    ) {
        pagingSource!!
    }.flow.cachedIn(viewModelScope)

    fun saveWeatherData(weatherDataItem: WeatherDataItem) {
        viewModelScope.launch {
            weatherRepository.saveWeatherDataItem(weatherDataItem)
        }
    }

    fun readDataFromFile(inputStream: InputStream) {
        var count: Int = 0
        viewModelScope.launch(Dispatchers.IO) {
            count = weatherRepository.getCount()
            if (count == 0) {
                val gson = Gson()
                var weatherDataItem: WeatherDataItem
                inputStream.bufferedReader().forEachLine {
                    weatherDataItem = gson.fromJson(it, WeatherDataItem::class.java)
                    saveWeatherData(weatherDataItem)
                }
            }
        }
    }

    fun onSubmitQuery(query: String) {
        currentQuery = query
        pagingSource?.invalidate()
    }

}
