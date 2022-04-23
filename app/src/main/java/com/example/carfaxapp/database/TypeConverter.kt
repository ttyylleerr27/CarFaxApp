package com.example.carfaxapp.database

import androidx.room.TypeConverter
import com.example.carfaxapp.network.ListInfo
import com.google.gson.Gson

class TypeConverter {
    @TypeConverter
    fun listToJsonString(value : List<ListInfo>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<ListInfo>::class.java).toList()
}