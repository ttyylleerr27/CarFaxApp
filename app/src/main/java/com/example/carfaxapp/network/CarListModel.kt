package com.example.carfaxapp.network

data class CarListModel(val items: ArrayList<CarInfoDetailed>)
data class CarInfoBasic(val carInfo: CarInfoDetailed)
data class CarInfoDetailed(val photo: ImageLinks, val info: String, val location: String, val mileage: String, val price: String)
data class ImageLinks(val carImage: String)