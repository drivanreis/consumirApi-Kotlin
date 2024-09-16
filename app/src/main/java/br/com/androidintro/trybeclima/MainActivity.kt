package br.com.androidintro.trybeclima

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.androidintro.trybeclima.models.CurrentWeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openWeatherService = OpenWeatherService.instance

        val callOpenWeatherService = openWeatherService.getCurrentWeatherData(
            "Fortaleza",
            "0e3190d11a30259aed88ee2c3de8a9ef"
        )

        callOpenWeatherService.enqueue(object : Callback<CurrentWeatherData> {
            override fun onResponse(
                call: Call<CurrentWeatherData>,
                response: Response<CurrentWeatherData>
            ) {
                val weatherData = response.body()
                Log.i("Trybe-Clima-Request", "$weatherData")
            }

            override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                Log.e("Trybe-Clima-Request","Ocorreu um erro durante a requisição: ${t.message}")
            }

        })
    }
}