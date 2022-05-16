package com.fiona.trafficnews.api

import example.fiona.pixabay.di.component.ApiComponent
import example.fiona.pixabay.di.component.DaggerApiComponent
import javax.inject.Inject


class ApiHelper() {
    @Inject
    lateinit var apiService: ApiService

    init {
        val apiComponent: ApiComponent = DaggerApiComponent.create()
        apiComponent.inject(this)
    }


    fun userLogin(params: HashMap<String, String>) =
        apiService.userLogin("https://watch-master-staging.herokuapp.com/api/login/", params)

    fun userUpdate(token:String,params: HashMap<String, Int>, objectId: String) = apiService.userUpdate(
        token,"https://watch-master-staging.herokuapp.com/api/users/" + objectId, params
    )

    fun getNews() = apiService.getNews("https://tcgbusfs.blob.core.windows.net/dotapp/news.json")
}

