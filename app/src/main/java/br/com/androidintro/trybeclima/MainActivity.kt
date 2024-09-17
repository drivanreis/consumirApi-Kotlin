package br.com.androidintro.trybeclima

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.androidintro.trybeclima.models.CurrentWeatherData
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val cityTempTextView : TextView by lazy { findViewById(R.id.main_city_temp_text_view) }
    private val cityNameEditText : EditText by lazy { findViewById(R.id.main_city_name_edit_text) }
    private val cityNameTextView : TextView by lazy { findViewById(R.id.main_city_name_text_view) }
    private val cityNameInputLayout : TextInputLayout by lazy { findViewById(R.id.main_city_name_input_Layout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityNameTextView.text = "-"

        cityNameInputLayout.setEndIconOnClickListener {

            val openWeatherService = OpenWeatherService.instance

//            val citeName = cityNameEditText.text.toString()

            Toast.makeText(baseContext,"Cidade: ${cityNameEditText.text.toString()}", Toast.LENGTH_LONG).show()

            val callOpenWeatherService = openWeatherService.getCurrentWeatherData(
                cityNameEditText.text.toString(),
                "0e3190d11a30259aed88ee2c3de8a9ef"
            )

            callOpenWeatherService.enqueue(object : Callback<CurrentWeatherData> {
                override fun onResponse(
                    call: Call<CurrentWeatherData>,
                    response: Response<CurrentWeatherData>
                ) {
                    val weatherData = response.body()
//                    Log.i("Trybe-Clima-Request", "$weatherData")
                    if (weatherData != null) {
                        cityTempTextView.text = "${ weatherData.main.temp.toInt() } ºC"
                        cityNameTextView.text = weatherData.name
                    }
                }

                override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                    Log.e("Trybe-Clima-Request","Ocorreu um erro durante a requisição: ${t.message}")
                }

            })
        }


    }
}