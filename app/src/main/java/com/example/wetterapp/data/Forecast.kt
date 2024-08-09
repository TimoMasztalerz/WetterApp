package com.example.wetterapp.data
//5 Tage vorschau
data class Forecast(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherCondition: String
)
