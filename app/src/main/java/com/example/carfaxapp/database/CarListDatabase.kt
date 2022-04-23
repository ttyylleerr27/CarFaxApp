package com.example.carfaxapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carfaxapp.network.CarListModel

@Database(entities = [CarListModel::class], version = 1, exportSchema = false)
abstract class CarListDatabase : RoomDatabase() {

    abstract val carListDao: CarListDao

    companion object {

        @Volatile
        private var INSTANCE: CarListDatabase? = null

        fun getInstance(context: Context): CarListDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CarListDatabase::class.java,
                        "car_list_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}