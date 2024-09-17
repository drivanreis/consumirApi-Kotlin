package br.com.androidintro.trybeclima

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.androidintro.trybeclima.models.CurrentWeatherData
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val cityTempTextView : TextView by lazy { findViewById(R.id.main_city_temp_text_view) }
    private val cityNameEditText : EditText by lazy { findViewById(R.id.main_city_name_edit_text) }
    private val cityNameTextView : TextView by lazy { findViewById(R.id.main_city_name_text_view) }
    private val cityNameInputLayout : TextInputLayout by lazy { findViewById(R.id.main_city_name_input_Layout) }

    private val openWeatherService = OpenWeatherService.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityNameTextView.text = "-"

        cityNameInputLayout.setEndIconOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(cityNameEditText.windowToken, 0)

            val progressDialog = showLoadingDialog(this)

//            val openWeatherService = OpenWeatherService.instance

//            val citeName = cityNameEditText.text.toString()

            Toast.makeText(baseContext,"Cidade: ${cityNameEditText.text.toString()}", Toast.LENGTH_LONG).show()

            CoroutineScope(Dispatchers.IO).launch {
                val responseOpenWeatherService = openWeatherService.getCurrentWeatherData(
                    cityNameEditText.text.toString(),
                    "0e3190d11a30259aed88ee2c3de8a9ef"
                )
                if (responseOpenWeatherService.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        val weatherData = responseOpenWeatherService.body()!!
//                    Log.i("Trybe-Clima-Request", "$weatherData")
                        cityTempTextView.text = "${ weatherData.main.temp.toInt() } ºC"
                        cityNameTextView.text = weatherData.name
                    }
                }
                progressDialog.dismiss()
            }

//            val responseOpenWeatherService = openWeatherService.getCurrentWeatherData(
//                cityNameEditText.text.toString(),
//                "0e3190d11a30259aed88ee2c3de8a9ef"
//            )

//            callOpenWeatherService.enqueue(object : Callback<CurrentWeatherData> {
//                override fun onResponse(
//                    call: Call<CurrentWeatherData>,
//                    response: Response<CurrentWeatherData>
//                ) {
//                    val weatherData = response.body()
////                    Log.i("Trybe-Clima-Request", "$weatherData")
//                    if (weatherData != null) {
//                        cityTempTextView.text = "${ weatherData.main.temp.toInt() } ºC"
//                        cityNameTextView.text = weatherData.name
//                    }
//                }
//
//                override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
//                    Log.e("Trybe-Clima-Request","Ocorreu um erro durante a requisição: ${t.message}")
//                }
//
//            })
        }
    }

    fun showLoadingDialog(context: Context) : ProgressDialog{
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Carregando...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        return progressDialog
    }
}