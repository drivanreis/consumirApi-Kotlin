package br.com.androidintro.trybeclima.data.datasource.models

import com.google.gson.annotations.SerializedName

data class RainData(
    @SerializedName("1h")
    val oneHour: Double,
    @SerializedName("3h")
    val threeHour: Double
)
