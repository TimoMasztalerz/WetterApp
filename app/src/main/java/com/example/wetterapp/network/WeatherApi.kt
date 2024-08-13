package com.example.wetterapp.network

import com.example.wetterapp.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WeatherApi {
    private const val BASE_URL = "https://api.meteomatics.com/"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val builder = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", okhttp3.Credentials.basic(
                    BuildConfig.WEATHER_API_USERNAME,
                    BuildConfig.WEATHER_API_PASSWORD
                ))
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(builder)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}