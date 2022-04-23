package com.example.carfaxapp.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarListModel(val listings: ArrayList<ListInfo>) : Parcelable

@Parcelize
data class ListInfo(val make: String,
                    val mileage: Int,
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
