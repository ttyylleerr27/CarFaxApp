package com.example.carfaxapp.network

data class CarListModel(val listings: ArrayList<ListInfo>)
data class ListInfo(val make: String,
                    val mileage: Int,
                    val model: String,
                    val listPrice: Int,
                    val images: FirstPhoto,
                    val dealer: Dealer
                    )
data class FirstPhoto(val firstPhoto: Image)
data class Image(val small: String)
data class Dealer(val city: String, val state: String, val phone: String)
