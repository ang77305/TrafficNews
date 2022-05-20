package com.fiona.trafficnews.di.module

import com.fiona.trafficnews.api.ApiHelper
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class ApiHelperModule {

    @Singleton
    @Provides
        fun providesApiManager(): ApiHelper {
        return ApiHelper()
    }
}