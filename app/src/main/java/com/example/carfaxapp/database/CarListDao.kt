package com.example.carfaxapp.database

import androidx.room.*
import com.example.carfaxapp.network.CarListModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CarListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCarList(data:CarListModel) : Completable

    @Query("SELECT * FROM car_list")
    fun getList(): Single<CarListModel>
}