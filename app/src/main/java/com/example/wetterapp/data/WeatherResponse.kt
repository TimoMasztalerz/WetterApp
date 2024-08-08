package com.example.wetterapp.data

import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "data") val data: List<WetterDaten>
) {
    fun getValueOrNA(parameter: String): String? {
        val value = data.firstOrNull { it.parameter == parameter }
            ?.coordinates?.firstOrNull()
            ?.dates?.firstOrNull()
            ?.value
        return value?.takeIf { it != 0.0 }?.let { String.format("%.1f", it) }
    }
}

data class WetterDaten(
    @Json(name = "parameter") val parameter: String,
    @Json(name = "coordinates") val coordinates: List<Location>

)

data class Location(
    @Json(name = "lat") val lat: Double,
    @Json(name = "lon") val lon: Double,
    @Json(name = "dates") val dates: List<DateValue>
)

data class DateValue(
    @Json(name = "date") val date: String,
    @Json(name = "value") val value: Double
)
