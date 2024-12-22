package com.example.jetweatherforecast.network

import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

// https://api.openweathermap.org/data/2.5/forecast/daily?q=lisbon&appid=ed60fcfbd110ee65c7150605ea8aceea&units=imperial

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units : String = "imperial",
        @Query("appid") appid : String = Constants.API_KEY

    ) : Weather
}