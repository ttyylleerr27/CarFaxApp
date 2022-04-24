package com.example.carfaxapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.carfaxapp.network.CarListModel

@Database(entities = [CarListModel::class], version = 2, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class CarListDatabase : RoomDatabase() {
    abstract fun carListDao(): CarListDao

    companion object {

        @Volatile
        private var INSTANCE: CarListDatabase? = null

        fun getInstance(context: Context): CarListDatabase =
            INSTANCE ?: synchronized(this)
            {
                INSTANCE ?: buildInstance(context).also{
                    INSTANCE = it
                }
        }

        private fun buildInstance(context: Context) =
            Room.databaseBuilder(context, CarListDatabase::class.java, "CarListDB")
                .fallbackToDestructiveMigration()
                .build()
    }
}