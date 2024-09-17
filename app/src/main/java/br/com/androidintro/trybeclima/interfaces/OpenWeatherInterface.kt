package br.com.androidintro.trybeclima.interfaces

import br.com.androidintro.trybeclima.models.CurrentWeatherData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherInterface {

    @GET("weather")
    suspend fun getCurrentWeatherData(
        @Query("q") citeName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ) : Response<CurrentWeatherData>
}