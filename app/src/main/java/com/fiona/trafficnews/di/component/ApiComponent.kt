package com.fiona.trafficnews.di.component

import com.fiona.trafficnews.api.ApiHelper
import dagger.Component

import com.fiona.trafficnews.di.module.ApiModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(apiHelper: ApiHelper)
}
