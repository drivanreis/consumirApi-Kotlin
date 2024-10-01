package br.com.androidintro.trybeclima.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    private var _cityName = MutableLiveData("-")
    val cityName: LiveData<String>
        get() = _cityName

    private var _temp = MutableLiveData(0.0)
    val temp: LiveData<Double>
        get() = _temp
}