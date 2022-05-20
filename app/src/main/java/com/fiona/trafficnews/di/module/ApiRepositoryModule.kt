package com.fiona.trafficnews.di.module

import com.fiona.trafficnews.ApiRepository
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class ApiRepositoryModule {

    @Singleton
    @Provides
    fun providesUserRepository(): ApiRepository {
        return ApiRepository()
    }
}