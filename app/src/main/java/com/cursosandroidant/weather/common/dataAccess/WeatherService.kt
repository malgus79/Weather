package com.cursosandroidant.weather.common.dataAccess

import com.cursosandroidant.weather.common.entities.WeatherForecastEntity
import com.cursosandroidant.weather.common.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * esta interface debe recibir el complemento de la ruta:
 * https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}
 * ruta base: https://api.openweathermap.org/
 * complemento del servicio: data/2.5/onecall
 *
 * @Get (se usa retrofit)
 * se debe crear Constants
 * **/

interface WeatherService {
    @GET(Constants.ONE_CALL_PATH)
    suspend fun getWeatherForecastByCoordinates(
        @Query(Constants.LATITUDE_PARAM) lat: Double,
        @Query(Constants.LONGITUDE_PARAM) lon: Double,
        @Query(Constants.APP_ID_PARAM) appId: String,
        @Query(Constants.EXCLUDE_PARAM) exclude: String,
        @Query(Constants.UNITS_PARAM) units: String,
        @Query(Constants.LANGUAGE_PARAM) lang: String
    ) : WeatherForecastEntity  //data class principal
}