package kr.hs.dgsw.weathermap.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.common.collect.Lists
import kr.hs.dgsw.weathermap.R
import kr.hs.dgsw.weathermap.data.data_source.CityLocalDataSource
import kr.hs.dgsw.weathermap.data.model.entity.Weather
import kr.hs.dgsw.weathermap.data.model.response_model.WeatherResponse
import kr.hs.dgsw.weathermap.data.repository.CityRepository
import kr.hs.dgsw.weathermap.databinding.ActivityMainBinding
import kr.hs.dgsw.weathermap.presentation.view_model.MainViewModel
import kr.hs.dgsw.weathermap.presentation.view_model_factory.MainViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewModel = ViewModelProvider(this, MainViewModelFactory(this)).get(MainViewModel::class.java)
        val weatherAdapter = MainWeatherAdapter(this)
        binding.vm = viewModel
        binding.adapter = weatherAdapter
        binding.lifecycleOwner = this



        viewModel.cityRepository.signal.observe(this, {
            viewModel.fetchWeatherList()
            viewModel.weatherList.observe(this, {
                weatherAdapter.submitList(it.sortedBy { it.name })
            })
        })

    }
}