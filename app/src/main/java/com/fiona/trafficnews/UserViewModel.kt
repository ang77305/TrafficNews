package com.fiona.trafficnews

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.fiona.trafficnews.data.Resource
import com.fiona.trafficnews.data.UserModel
import example.fiona.pixabay.di.component.ApiRepositoryComponent
import example.fiona.pixabay.di.component.DaggerApiRepositoryComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserViewModel() : ViewModel() {
    var userInfoLiveData = MutableLiveData<Resource<UserModel>>()
    var userInfoUpdateLiveData = MutableLiveData<Resource<UserModel>>()
    var userName = MutableLiveData<String>()
    var userNameError = MutableLiveData<String>()
    var pwdError = MutableLiveData<String>()
    lateinit var userModel: UserModel

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

    @SuppressLint("CheckResult")
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
            })
    }

    @SuppressLint("CheckResult")
    fun userUpdate(timezone: Int) {

        val map = hashMapOf(
            "timezone" to timezone,
        )
        apiRepository
            .updateUser(userInfoLiveData.value?.data?.sessionToken!!,map,
                userInfoLiveData.value?.data?.objectId!!
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userInfoLiveData.value!!.data!!.timezone=timezone
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
            })
    }

}