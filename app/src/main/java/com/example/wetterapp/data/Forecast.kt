package com.example.wetterapp.data

data class Forecast(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherCondition: String
)
