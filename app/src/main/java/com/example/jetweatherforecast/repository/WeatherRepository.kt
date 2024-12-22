package com.example.jetweatherforecast.repository

import android.util.Log
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.network.WeatherApi
import retrofit2.HttpException
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api : WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String)
    :DataOrException<Weather,Boolean,Exception>{

        val response  = try {
            Log.d("avail","api got")
            api.getWeather(query = cityQuery, units = units)
        } catch (e:Exception) {

            if (e is HttpException) {
                when (e.code()) {
                    401 -> {
                        Log.d("api","api expired")
                    }
                    403 -> {
                        Log.d("api","API key does not have permission")
                    }
                    else -> {
                        // Other HTTP errors
                        Log.e("WeatherRepository", "HTTP error: ${e.code()} - ${e.message()}")
                        return DataOrException(
                            data = null,  // No data available
                            loading = false,  // Indicate failure
                            e = e  // Exception as error
                        )
                    }
                }
            }
            Log.d("not_avail","api not got")
            Log.d("rex","getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE","getWeather: $response")
        return DataOrException(data = response)

    }
}