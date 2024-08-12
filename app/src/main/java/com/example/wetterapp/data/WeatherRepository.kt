package com.example.wetterapp.data

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import android.util.Base64
import com.example.wetterapp.network.WeatherResponse
import com.example.wetterapp.network.WeatherApi

class WeatherRepository {
    private val api = WeatherApi.service
    //Authentifizierung
    private fun getAuthHeader(): String {
        val referece = "${WeatherApi.USERNAME}:${WeatherApi.PASSWORD}"
        return "Basic " + Base64.encodeToString(referece.toByteArray(), Base64.NO_WRAP)
    }
    //Städte mit Koorinaten
    fun getCities(): List<City> {
        return listOf(
            City("Berlin", 52.520551, 13.461804),
            City("Hamburg", 53.551086, 9.993682),
            City("Munich", 48.135124, 11.581981),
            City("Frankfurt", 50.110924, 8.682127),
            City("Cologne", 50.937531, 6.960279),
            City("Freiburg im Breisgau", 47.9957, 7.8421),
            City("Stuttgart", 48.775846, 9.182932),
            City("Dresden", 51.0504, 13.7373),
            City("Leipzig", 51.3394, 12.3816),
            City("Nürnberg", 49.4544, 11.0771),
            City("Düsseldorf", 51.2254, 6.7763),
            City("Bremen", 53.0736, 8.803056),
            City("Heidelberg", 49.4091, 8.6937),
            City("Rostock", 54.0925, 12.112),
            City("Augsburg", 48.3746, 10.8934),
            City("Trier", 49.7561, 6.6389),
            City("Schwerin", 53.6333, 11.4042),
            City("Erfurt", 50.9734, 11.0275),
            City("Kempten", 47.7225, 10.3092),
        )
    }
    //Wetter nach Stadt abrufen. Gibt es bessere möglichkeiten für Timezonedata??
    suspend fun getWeatherForCity(city: City): WeatherResponse {
        val datetime = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val location = "${city.latitude},${city.longitude}"
        return api.getCurrentWeather(datetime, location, getAuthHeader())
    }
}
