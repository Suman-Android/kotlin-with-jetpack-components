package com.amadeus.myapplication.models

import com.amadeus.myapplication.common.footer.BaseUiState
import com.amadeus.myapplication.utils.Utility

data class WeatherItemUiState(private val weatherDataItem: WeatherDataItem) : BaseUiState() {

    private fun getCityName() = weatherDataItem.city?.name

    fun getCityCountryName() = getCityName()+", "+getCountryName()

    fun getCityID() = weatherDataItem.city?.id

    private fun getTime() = weatherDataItem.time

    fun getUpdatedAt() = "Updated at " + Utility.convertLongToTime(getTime().toLong())

    private fun getCountryName() = weatherDataItem.city?.country

    fun getMain() = weatherDataItem.weather?.get(0)?.main

    fun getStatus() = weatherDataItem.main?.temp

    fun getMinTemp() = "Min Temp:" +weatherDataItem.main?.temp_min

    fun getMaxTemp() = "Max Temp:" +weatherDataItem.main?.temp_max


}