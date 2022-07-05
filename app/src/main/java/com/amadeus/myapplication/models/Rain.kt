package com.amadeus.myapplication.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Rain(
    @field:SerializedName("3h") val threeHour: Double?,
    @field:SerializedName("1h") val oneHour: Double?

)