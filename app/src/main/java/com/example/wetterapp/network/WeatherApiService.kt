package com.example.wetterapp.network

import com.example.wetterapp.data.Forecast
import com.example.wetterapp.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface WeatherApiService {
    @GET("{datetime}/t_2m:C,relative_humidity_2m:p,wind_speed_10m:ms/{location}/json")
    suspend fun getCurrentWeather(
        @Path("datetime") datetime: String,
        @Path("location") location: String,
        @Header("Authorization") authHeader: String
    ): WeatherResponse

    @GET("your/forecast/endpoint")
    fun getForecast(): List<Forecast>
}

