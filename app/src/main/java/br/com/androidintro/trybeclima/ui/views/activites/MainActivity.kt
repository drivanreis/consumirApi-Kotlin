package br.com.androidintro.trybeclima.ui.views.activites

//import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.androidintro.trybeclima.R
import br.com.androidintro.trybeclima.data.datasource.models.DayWeather
import br.com.androidintro.trybeclima.data.repository.OpenWeatherServiceObject
import br.com.androidintro.trybeclima.ui.adapters.DayWeatherAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    private val cityTempTextView : TextView by lazy { findViewById(R.id.main_city_temp_text_view) }
    private val cityNameEditText : EditText by lazy { findViewById(R.id.main_city_name_edit_text) }
    private val cityNameTextView : TextView by lazy { findViewById(R.id.main_city_name_text_view) }
    private val cityNameInputLayout : TextInputLayout by lazy { findViewById(R.id.main_city_name_input_Layout) }

    private val dayRecyclerView: RecyclerView by lazy { findViewById(R.id.main_days_recycler_view) }

    private val openWeatherService = OpenWeatherServiceObject.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityNameTextView.text = "-"

        cityNameInputLayout.setEndIconOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(cityNameEditText.windowToken, 0)

//            val progressDialog = showLoadingDialog(this)

//            val openWeatherService = OpenWeatherServiceObject.instance

//            val citeName = cityNameEditText.text.toString()

            Toast.makeText(baseContext,"Cidade: ${cityNameEditText.text.toString()}", Toast.LENGTH_LONG).show()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val responseOpenWeatherService = openWeatherService.getCurrentWeatherData(
                        cityNameEditText.text.toString(),
                        "0e3190d11a30259aed88ee2c3de8a9ef"
                    )
                    if (responseOpenWeatherService.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            val weatherData = responseOpenWeatherService.body()!!
//                    Log.i("Trybe-Clima-Request", "$weatherData")
                            cityTempTextView.text = "${weatherData.main.temp.toInt()} ºC"
                            cityNameTextView.text = weatherData.name
                        }
                    } else {
                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setTitle("Trybe Clima")
                            .setMessage("Erro de requisição: ${responseOpenWeatherService.message()}")
                            .setCancelable(false)
                            .setPositiveButton("OK") {dialog, _-> dialog.dismiss()}
                            .show()
                    }
//                    progressDialog.dismiss()
                } catch (e: HttpException) {
                    Log.e("HttpException",e.message.toString())
                }
                catch (e: Exception) {
                    Log.e("Corrotina",e.message.toString())
                }
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

        val daysWeather = listOf(
            DayWeather(
                "SEG",
                25.5,
                27.0,
                23.0
            ),
            DayWeather(
                "TER",
                22.5,
                30.0,
                21.0
            ),            DayWeather(
                "QUA",
                22.5,
                30.0,
                21.0
            ),
            DayWeather(
                "QUI",
                22.5,
                30.0,
                21.0
            ),
            DayWeather(
                "SEX",
                22.5,
                30.0,
                21.0
            )
        )

        val adapter = DayWeatherAdapter(daysWeather)
        dayRecyclerView.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
        dayRecyclerView.adapter = adapter

    }

//    fun showLoadingDialog(context: Context) : ProgressDialog{
//        val progressDialog = ProgressDialog(context)
//        progressDialog.setMessage("Carregando...")
//        progressDialog.setCancelable(false)
//        progressDialog.show()
//        return progressDialog
//    }
}