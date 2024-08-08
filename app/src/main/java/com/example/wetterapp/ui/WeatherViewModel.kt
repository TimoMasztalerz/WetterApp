package com.example.wetterapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetterapp.data.City
import com.example.wetterapp.WeatherRepository
import com.example.wetterapp.data.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> = _cities
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData


    fun fetchCities() {
        viewModelScope.launch {
            try {
                val cityList = repository.getCities()
                _cities.value = cityList
            } catch (e: Exception) {
                // Handle error
            }
        }
    }


        fun fetchWeatherForCity(city: City) {
            viewModelScope.launch {
                try {
                    val weatherResponse = repository.getWeatherForCity(city)
                    _weatherData.value = weatherResponse
                } catch (e: Exception) {
                    Log.e("WeatherViewModel", "Error fetching data", e)
                }
            }
        }
    }

