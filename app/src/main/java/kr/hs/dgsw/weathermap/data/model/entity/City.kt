package kr.hs.dgsw.weathermap.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey
    val city: String
)
