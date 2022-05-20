package com.fiona.trafficnews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fiona.trafficnews.ApiRepository
import com.fiona.trafficnews.data.NewsDataModel
import com.fiona.trafficnews.data.Resource
import com.fiona.trafficnews.di.component.ApiRepositoryComponent
import com.fiona.trafficnews.di.component.DaggerApiRepositoryComponent

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject


class NewsViewModel : BaseViewModel() {

    var newsDataModel = MutableLiveData<Resource<NewsDataModel>>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var apiRepository: ApiRepository

    init {
        val apiRepositoryComponent: ApiRepositoryComponent =
            DaggerApiRepositoryComponent.create()
        apiRepositoryComponent.inject(this)
    }


    fun getNews() {

        apiRepository
            .getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newsDataModel.postValue(Resource.success(it))
                compositeDisposable.dispose()
            }, { throwable ->
                var message = ""
                message =
                    if (throwable is UnknownHostException || throwable is ConnectException)
                        "Check your internet connection and try again!"
                    else
                        "Something went wrong!"

                newsDataModel.postValue(
                    Resource.error(
                        message,
                        null
                    )

                )
            }).addDisposable()
    }


}