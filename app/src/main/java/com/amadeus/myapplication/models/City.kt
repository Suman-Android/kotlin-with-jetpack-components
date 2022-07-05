package com.amadeus.myapplication.models

import androidx.room.Embedded

data class City(
    @Embedded
    val coord: Coord?,
    val country: String?,
    val findname: String?,
    val id: Int?,
    val langs: List<Lang>?,
    val name: String?,
    val zoom: Int?
)