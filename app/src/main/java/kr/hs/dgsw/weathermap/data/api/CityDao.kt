package kr.hs.dgsw.weathermap.data.api

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kr.hs.dgsw.weathermap.data.model.entity.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(city: City): Completable

    @Query("SELECT * FROM City")
    fun getMyCities(): Single<List<City>>

    @Delete
    fun deleteCity(city: City): Completable
}