package br.com.androidintro.trybeclima

import br.com.androidintro.trybeclima.interfaces.OpenWeatherInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object OpenWeatherService {

    val instance: OpenWeatherInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory((GsonConverterFactory.create()))
            .build()

        retrofit.create(OpenWeatherInterface::class.java)
    }

}