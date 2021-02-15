package kr.hs.dgsw.weathermap.presentation.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.hs.dgsw.weathermap.data.model.entity.City
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse
import kr.hs.dgsw.weathermap.data.repository.CityRepository
import kr.hs.dgsw.weathermap.data.repository.WeatherRepository

class MainViewModel(private val context: Context) : ViewModel() {
    val cityRepository = CityRepository(context)
    private val weatherRepository = WeatherRepository()

    val myCities: LiveData<List<City>> = cityRepository.getMyCities()

    lateinit var weatherList: LiveData<List<WeatherResponse>>

    fun fetchWeatherList() {
        myCities.value?.let {
            weatherList = weatherRepository.getWeathers(it.map { return@map it.city })
        }
    }
}