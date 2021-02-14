package kr.hs.dgsw.weathermap.data.data_source

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.weathermap.data.api.CityDatabase
import kr.hs.dgsw.weathermap.data.model.entity.City
import java.sql.SQLException

class CityLocalDataSource(private val context: Context) {
    private val _myCites = MutableLiveData<List<City>>()
    val myCities: LiveData<List<City>>
        get() = _myCites

    private val compositeDisposable = CompositeDisposable()

    fun getMyCities() {
        try {
            CityDatabase.getInstance(context.applicationContext)?.getDao()?.getMyCities()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    {
                        _myCites.value = it
                    },
                    {
                        if (it is SQLException) {
                            Log.d("City Error", "${it.sqlState} : ${it.message}")
                        } else
                            Log.d("City Error", "${it.message}")
                    }
                )?.let {
                    compositeDisposable.add(
                        it
                    )
                }
        } catch (e: Exception) {
            Log.d("City Error", "${e.message}")
        }
    }

    fun addCity(s: String) {
        val city = City(s)

        try {
            CityDatabase.getInstance(context.applicationContext)?.getDao()?.insertCity(city)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    Log.d("City", "Insert ${city.city}")
                }, {
                    if (it is SQLException)
                        Log.d("City Error", "${it.sqlState} : ${it.message}")
                    else Log.d("City Error", "${it.message}")
                })
                ?.let {
                    compositeDisposable.add(it)
                }
        } catch (e: Exception) {
            Log.d("City Error", "${e.message}")
        }
    }

    fun deleteCity(s: String) {
        val city = City(s)

        try {
            CityDatabase.getInstance(context.applicationContext)?.getDao()?.deleteCity(city)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    Log.d("City", "Delete ${city.city}")
                }, {
                    if (it is SQLException)
                        Log.d("City Error", "${it.sqlState} : ${it.message}")
                    else Log.d("City Error", "${it.message}")
                })
                ?.let {
                    compositeDisposable.add(it)
                }
        } catch (e: Exception) {
            Log.d("City Error", "${e.message}")
        }
    }

}