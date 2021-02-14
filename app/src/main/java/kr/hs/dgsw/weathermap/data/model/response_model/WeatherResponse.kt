package kr.hs.dgsw.weathermap.data.model.response_model

import kr.hs.dgsw.weathermap.data.model.entity.Main
import kr.hs.dgsw.weathermap.data.model.entity.Weather
import kr.hs.dgsw.weathermap.data.model.entity.Wind

data class WeatherResponse(
    val main: Main,
    val name: String,
    val weather: List<Weather>,
    val wind: Wind
)