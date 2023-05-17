package com.cursosandroidant.weather.mainModule.model

import com.cursosandroidant.weather.common.dataAccess.WeatherService
import com.cursosandroidant.weather.common.entities.WeatherForecastEntity
import com.cursosandroidant.weather.common.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDatabase {
    //primera instancia de configuracion de retrofit:
    private val retrofit = Retrofit.Builder()
     .baseUrl(Constants.BASE_URL)
     .addConverterFactory(GsonConverterFactory.create())
     .build()

    //configuracion del servicio:
    private val service = retrofit.create(WeatherService::class.java)

    //definir la funcion: (obtener el pronóstico del tiempo por coordenadas)
    suspend fun getWeatherForecastByCoordinates(lat: Double, lon: Double, appId: String, exclude: String,
                                                units: String, lang: String) : WeatherForecastEntity =
     withContext(Dispatchers.IO){
        service.getWeatherForecastByCoordinates(lat, lon, appId, exclude, units, lang)
    }
    //IO = porque es una peticion a una base de datos remota
}