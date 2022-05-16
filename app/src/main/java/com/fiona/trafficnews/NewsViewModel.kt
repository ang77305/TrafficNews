package com.fiona.trafficnews

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiona.trafficnews.data.NewsDataModel
import com.fiona.trafficnews.data.Resource
import com.fiona.trafficnews.data.UserModel
import example.fiona.pixabay.di.component.ApiRepositoryComponent
import example.fiona.pixabay.di.component.DaggerApiRepositoryComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject


class NewsViewModel() : ViewModel() {

    var newsDataModel = MutableLiveData<Resource<NewsDataModel>>()

    @Inject
    lateinit var apiRepository: ApiRepository

    init {
        val apiRepositoryComponent: ApiRepositoryComponent =
            DaggerApiRepositoryComponent.create()
        apiRepositoryComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    fun getNews() {

        apiRepository
            .getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newsDataModel.postValue(Resource.success(it))
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
            })
    }
}