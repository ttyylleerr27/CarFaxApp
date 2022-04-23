package com.example.carfaxapp.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "car_list")
data class CarListModel(
    val listings: List<ListInfo>,
    @PrimaryKey(autoGenerate = true)
    val key: Int
    ) : Parcelable

@Parcelize
data class ListInfo(
    val mileage: Int,
    val make: String,
    val model: String,
    val currentPrice: Int,
    val images: FirstPhoto,
    val dealer: Dealer,
    val exteriorColor: String,
    val interiorColor: String,
    val engine: String,
    val year: Int,
    val fuel: String,
    val drivetype: String,
    val transmission: String,
    val bodytype: String
    ) : Parcelable

@Parcelize
data class FirstPhoto(val firstPhoto: Image) : Parcelable

@Parcelize
data class Image(val large: String) : Parcelable

@Parcelize
data class Dealer(val city: String, val state: String, val phone: String) : Parcelable
