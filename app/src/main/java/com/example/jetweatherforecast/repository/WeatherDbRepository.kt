package com.example.jetweatherforecast.repository

import com.example.jetweatherforecast.data.WeatherDao
import com.example.jetweatherforecast.model.Favorite
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.example.jetweatherforecast.model.Unit

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites() : Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite)  = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorites(favorite: Favorite) = weatherDao.deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun getFavById(city : String) : Favorite = weatherDao.getFavById(city)

    //Unit
    fun getUnits() : Flow<List<Unit>> = weatherDao.getUnits()

    suspend fun insertUnit(unit:Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()

}