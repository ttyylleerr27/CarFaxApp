package com.example.carfaxapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carfaxapp.network.CarListModel
import com.example.carfaxapp.network.RetrofitInstance
import com.example.carfaxapp.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LandingPageViewModel: ViewModel() {
    var carList: MutableLiveData<CarListModel> = MutableLiveData()

    fun getCarListObserver(): MutableLiveData<CarListModel>{
        return carList
    }

    fun makeApiCalls(){
        val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofitInstance.getCarListFromApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCarListObserverRx())
    }

    private fun getCarListObserverRx(): Observer<CarListModel> {
        return object: Observer<CarListModel> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: CarListModel) {
                carList.postValue(t)
            }

            override fun onError(e: Throwable) {
                carList.postValue(null)
            }

            override fun onComplete() {

            }
        }
    }
}