package com.fiona.trafficnews.di.component

import com.fiona.trafficnews.ApiRepository
import dagger.Component
import com.fiona.trafficnews.di.module.ApiHelperModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiHelperModule::class])
interface ApiHelperComponent {
    fun inject(apiRepository: ApiRepository)
}
