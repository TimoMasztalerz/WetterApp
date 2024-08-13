
package com.example.wetterapp.localdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warnings")
data class Warning(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: String
)




@Entity
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherCondition: String,

)

@Entity(tableName = "cities")
data class City(
    @PrimaryKey val name: String,
    val latitude: Double,
    val longitude: Double
)
