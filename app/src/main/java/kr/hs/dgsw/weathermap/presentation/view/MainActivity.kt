package kr.hs.dgsw.weathermap.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kr.hs.dgsw.weathermap.R
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
                            if (it) {
                                viewModel.cityRepository.addCity(cityName.text.toString())
                            } else {
                                Toast.makeText(this, "thisCity isn't valid name", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    .setNegativeButton("Cancel") { i, n -> }
                    .show()
        }


    }
}