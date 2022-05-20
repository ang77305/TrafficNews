package com.fiona.trafficnews.viewmodel

import androidx.lifecycle.MutableLiveData
import com.fiona.trafficnews.ApiRepository

import com.fiona.trafficnews.data.Resource
import com.fiona.trafficnews.data.UserModel
import com.fiona.trafficnews.di.component.ApiRepositoryComponent
import com.fiona.trafficnews.di.component.DaggerApiRepositoryComponent

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserViewModel : BaseViewModel() {
    var userInfoLiveData = MutableLiveData<Resource<UserModel>>()
    var userInfoUpdateLiveData = MutableLiveData<Resource<UserModel>>()
    var userName = MutableLiveData<String>()
    var userNameError = MutableLiveData<String>()
    var pwdError = MutableLiveData<String>()

    @Inject
    lateinit var apiRepository: ApiRepository

    init {
        val apiRepositoryComponent: ApiRepositoryComponent =
            DaggerApiRepositoryComponent.create()
        apiRepositoryComponent.inject(this)
    }


    fun checkInputValid(useName: String, pwd: String) {
        userNameError.value = ""
        pwdError.value = ""
        if (useName.isEmpty())
            userNameError.value = "Please input username"
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(useName).matches())
            userNameError.value = "Please input e-mail"
        else if (pwd.isEmpty())
            pwdError.value = "Please input password"
        else {
            userNameError.value = ""
            pwdError.value = ""
            userLoign(useName, pwd)
            userInfoLiveData.postValue(
                Resource.loading(null)
            )
        }
    }


    fun userLoign(useName: String, pwd: String) {

        val map = hashMapOf(
            "username" to useName,
            "password" to pwd,
        )
        apiRepository
            .userLogin(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userInfoLiveData.postValue(Resource.success(it))
            }, { throwable ->
                var message = ""
                message =
                    if (throwable is UnknownHostException || throwable is ConnectException)
                        "Check your internet connection and try again!"
                    else
                        "Please input corrct username and password!"

                userInfoLiveData.postValue(
                    Resource.error(
                        message,
                        null
                    )
                )
            }).addDisposable()
    }

    fun userUpdate(timezone: Int) {

        val map = hashMapOf(
            "timezone" to timezone,
        )
        userInfoLiveData.value?.data?.sessionToken?.let {
            userInfoLiveData.value?.data?.objectId?.let { it1 ->
                apiRepository
                    .updateUser(
                        it, map,
                        it1
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        userInfoLiveData.value?.data?.timezone = timezone
                        userInfoUpdateLiveData.postValue(Resource.success(it))
                    }, { throwable ->
                        var message = ""
                        message =
                            if (throwable is UnknownHostException || throwable is ConnectException)
                                "Check your internet connection and try again!"
                            else
                                "Please input corrct username and password!"

                        userInfoUpdateLiveData.postValue(
                            Resource.error(
                                message,
                                null
                            )
                        )
                    }).addDisposable()
            }
        }
    }

}