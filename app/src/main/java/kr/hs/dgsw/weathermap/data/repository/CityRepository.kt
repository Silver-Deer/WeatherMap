package kr.hs.dgsw.weathermap.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import kr.hs.dgsw.weathermap.data.data_source.CityLocalDataSource
import kr.hs.dgsw.weathermap.data.model.entity.City

class CityRepository(private val context: Context) {
    private val cityLocalDataSource = CityLocalDataSource(context)

    fun getMyCities(): LiveData<List<City>> {
        cityLocalDataSource.getMyCities()

        return cityLocalDataSource.myCities
    }

    fun addCity(city: String) {
        cityLocalDataSource.addCity(city)
    }

    fun deleteCity(city: String) {
        cityLocalDataSource.deleteCity(city)
    }
}