package com.example.carfaxapp.network

import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitService {
    @GET("assignment.json/")
    fun getCarListFromApi(): Observable<CarListModel>
}