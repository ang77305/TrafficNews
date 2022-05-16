package com.fiona.trafficnews

import com.fiona.trafficnews.api.ApiHelper
import com.fiona.trafficnews.data.NewsDataModel
import com.fiona.trafficnews.data.UserModel
import example.fiona.pixabay.di.component.ApiHelperComponent
import example.fiona.pixabay.di.component.DaggerApiHelperComponent
import io.reactivex.Single
import javax.inject.Inject

class ApiRepository {
    @Inject
    lateinit var apiHelper: ApiHelper
    init {
        val apiHelperComponent: ApiHelperComponent = DaggerApiHelperComponent.create()
        apiHelperComponent.inject(this)
    }

    fun userLogin(params: HashMap<String, String>): Single<UserModel> {
        return apiHelper.userLogin(params)
    }

    fun updateUser(token :String,params: HashMap<String, Int>,objectId:String): Single<UserModel> {
        return apiHelper.userUpdate(token ,params, objectId)
    }

    fun getNews(): Single<NewsDataModel> {
        return apiHelper.getNews()
    }
}