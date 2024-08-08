package com.example.wetterapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WeatherApi {
    private const val BASE_URL = "https://api.meteomatics.com/"
    const val USERNAME = "syntax_stadelmayer_timo"
    const val PASSWORD = "U6T2s26Cae"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}
