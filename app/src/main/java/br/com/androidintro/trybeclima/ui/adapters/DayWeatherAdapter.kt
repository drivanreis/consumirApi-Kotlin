package br.com.androidintro.trybeclima.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.androidintro.trybeclima.R
import br.com.androidintro.trybeclima.data.datasource.models.DayWeather

class DayWeatherAdapter(private val dayWeather:List<DayWeather>) : RecyclerView.Adapter<DayWeatherAdapter.DaysWeatherViewHolder> (){
    class DaysWeatherViewHolder(view:View): ViewHolder(view){
        val day: TextView = view.findViewById(R.id.item_day_text_view)
        val dayTemp: TextView = view.findViewById(R.id.item_day_temp_text_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_days_weather, parent,false)
        return DaysWeatherViewHolder(view)
    }

    override fun getItemCount() = dayWeather.size

    override fun onBindViewHolder(holder: DaysWeatherViewHolder, position: Int) {
        holder.day.text = dayWeather[position].day
        holder.dayTemp.text = "${dayWeather[position].temp}º"
    }
}