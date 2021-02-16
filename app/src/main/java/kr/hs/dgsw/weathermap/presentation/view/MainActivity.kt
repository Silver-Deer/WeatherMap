package kr.hs.dgsw.weathermap.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kr.hs.dgsw.weathermap.R
import kr.hs.dgsw.weathermap.data.api.CityDatabase
import kr.hs.dgsw.weathermap.databinding.ActivityMainBinding
import kr.hs.dgsw.weathermap.presentation.view_model.MainViewModel
import kr.hs.dgsw.weathermap.presentation.view_model_factory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewModel = ViewModelProvider(this, MainViewModelFactory(this)).get(MainViewModel::class.java)
        val weatherAdapter = MainWeatherAdapter(this)
        weatherAdapter.clickEvent = {
            AlertDialog.Builder(this)
                    .setMessage("Delete this city?")
                    .setPositiveButton("Delete") { i, n ->
                        viewModel.cityRepository.deleteCity(it.name)
                    }
                    .setNegativeButton("Cancel") { i, n ->

                    }
                    .show()
        }
        binding.vm = viewModel
        binding.adapter = weatherAdapter
        binding.lifecycleOwner = this

        viewModel.cityRepository.signal.observe(this, {
            viewModel.fetchWeatherList()
            Log.d("Weather", "${viewModel.weatherList.value?.size}")
            viewModel.weatherList.observe(this, {
                weatherAdapter.submitList(it.sortedBy { it.id })
            })
        })

        findViewById<FloatingActionButton>(R.id.btn_add_city).setOnClickListener {
            val cityName = EditText(this)
            AlertDialog.Builder(this)
                    .setMessage("Add city name")
                    .setView(cityName)
                    .setPositiveButton("ADD") { i, n ->
                        val isValid = viewModel.isValidCity(cityName.text.toString())
                        isValid.observe(this, {
                            if (it == true) {
                                viewModel.cityRepository.addCity(cityName.text.toString())
                            } else if (it == false) {
                                Toast.makeText(this, "ThisCity isn't valid name", Toast.LENGTH_SHORT).show()
                            } else {
                                return@observe
                            }
                            viewModel.resetValidValue()
                            isValid.removeObservers(this)
                        })
                    }
                    .setNegativeButton("Cancel") { i, n -> }
                    .show()
        }


    }
}