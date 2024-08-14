package com.example.wetterapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetterapp.data.City
import com.example.wetterapp.data.WeatherRepository
import com.example.wetterapp.network.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> = _cities

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val _weatherSymbolUrl = MutableLiveData<String>()
    val weatherSymbolUrl: LiveData<String> = _weatherSymbolUrl

    init {
        fetchCities()
    }

    fun fetchCities() {
        viewModelScope.launch {
            try {
                val cityList = repository.getCities()
                _cities.value = cityList
            } catch (e: Exception) {
                // Fehlerbehandlung
            }
        }
    }

    fun fetchWeatherForCity(city: City) {
        viewModelScope.launch {
            try {
                val weatherResponse = repository.getWeatherForCity(city)
                _weatherData.value = weatherResponse
                _weatherSymbolUrl.value = weatherResponse.getWeatherSymbolUrl()
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather for ${city.name}", e)
            }
        }
    }
}