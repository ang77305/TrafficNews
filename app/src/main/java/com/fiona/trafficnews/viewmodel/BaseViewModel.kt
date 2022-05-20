package com.fiona.trafficnews.viewmodel


import androidx.lifecycle.ViewModel

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BaseViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null


    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
            compositeDisposable = null
        }
    }

    protected fun Disposable.addDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(this)
    }

}