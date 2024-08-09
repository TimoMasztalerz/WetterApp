package com.example.wetterapp.data
//5 Tage vorschau
data class NetworkForecast(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherCondition: String
)
