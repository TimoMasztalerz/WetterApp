// WeatherApi.kt
package com.example.wetterapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header

object WeatherApi {
    val apiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

private const val BASE_URL = "https://api.meteomatics.com/"

private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()

interface WeatherApiService {
    @GET("{datetime}/t_2m:C,relative_humidity_2m:p,wind_speed_10m:ms/{location}/json")
    suspend fun getCurrentWeather(
        @Path("datetime") datetime: String,
        @Path("location") location: String,
        @Header("Authorization") authHeader: String
    ): WeatherResponse
}


