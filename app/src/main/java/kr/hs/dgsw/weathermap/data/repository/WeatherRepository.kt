package kr.hs.dgsw.weathermap.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.weathermap.data.data_source.WeatherRemoteDataSource
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse

class WeatherRepository {
    private val weatherRemoteDataSource = WeatherRemoteDataSource()

    fun getWeather(city: String): LiveData<WeatherResponse> {
        weatherRemoteDataSource.getWeather(city)

        return weatherRemoteDataSource.weatherResponse
    }

    fun getWeathers(cities: List<String>): LiveData<List<WeatherResponse>> {
        weatherRemoteDataSource.resetList()
        weatherRemoteDataSource.getWeatherList(cities)

        return weatherRemoteDataSource.weatherResponseList
    }

}