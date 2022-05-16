package com.fiona.trafficnews.api

import com.fiona.trafficnews.data.NewsDataModel
import com.fiona.trafficnews.data.UserModel
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @Headers(
        "Content-type:application/json",
        "X-Parse-Application-Id:vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD"
    )
    @POST
    fun userLogin(@Url url: String, @Body params: HashMap<String, String>): Single<UserModel>


    @Headers(
        "Content-type:application/json",
        "X-Parse-Application-Id:vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD"
    )
    @PUT
    fun userUpdate(
        @Header("X-Parse-Session-Token") token: String,
        @Url url: String,
        @Body params: HashMap<String, Int>
    ): Single<UserModel>

    @GET
    fun getNews(@Url url: String): Single<NewsDataModel>
}
