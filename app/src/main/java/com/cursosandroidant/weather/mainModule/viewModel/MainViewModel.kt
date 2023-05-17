package com.cursosandroidant.weather.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosandroidant.weather.R
import com.cursosandroidant.weather.common.entities.WeatherForecastEntity
import com.cursosandroidant.weather.mainModule.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    //definir las variables globales:
    private val repository = MainRepository()

    //reflejar los cambios de la consulta general en la base de datos:
    private val result = MutableLiveData<WeatherForecastEntity>()
    fun getResult(): LiveData<WeatherForecastEntity> = result

    //mostrar un mensaje con snackbar:
    private val snackbarMsg = MutableLiveData<Int>()
    fun getSnackbarMsg() = snackbarMsg

    //visualizacion de la progessbar:
    private val loaded = MutableLiveData<Boolean>()
    fun isLoaded() = loaded

    //definir le metodo: (la fun recibe los mnismos argumentos que el repositorio)
    suspend fun getWeatherAndForecast(lat: Double, lon: Double, appId: String, exclude: String, units: String,
                                      lang: String){
        viewModelScope.launch {
            try {
                loaded.value = false
                val resultServer = repository.getWeatherAndForecast(lat, lon, appId, exclude, units, lang)
                result.value = resultServer
                if (resultServer.hourly == null || resultServer.hourly.isEmpty())
                    snackbarMsg.value = R.string.main_error_empty_forecast
            } catch (e: Exception) {
                snackbarMsg.value = R.string.main_error_server
            } finally {
                loaded.value = true //hacer invisible el progressbar
            }
        }
    }
}