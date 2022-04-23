package com.example.carfaxapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carfaxapp.database.CarListDatabase
import com.example.carfaxapp.network.CarListModel
import com.example.carfaxapp.network.RetrofitInstance
import com.example.carfaxapp.network.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LandingPageViewModel: ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    private var database: CarListDatabase ?= null
    var carList: MutableLiveData<CarListModel> = MutableLiveData()

    fun setInstanceOfDatabase(database: CarListDatabase){
        this.database = database
    }

    fun saveDataIntoDb(data: CarListModel){
        database?.carListDao()?.insertCarList(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
            },{
            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getDataFromDb(){
        database?.carListDao()?.getList()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                carList.postValue(it)
            },{
            })?.let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

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