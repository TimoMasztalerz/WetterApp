package com.example.wetterapp.localdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

class Dao {
}


@Dao
interface WarningDao {
    @Insert
    suspend fun insert(warning: Warning)

    @Query("SELECT * FROM warnings")
    suspend fun getAllWarnings(): List<Warning>
}

@Dao
interface ForecastDao {
    @Insert
    suspend fun insertAll(forecasts: List<Forecast>)

    @Query("SELECT * FROM forecast")
    suspend fun getAllForecasts(): List<Forecast>
}
