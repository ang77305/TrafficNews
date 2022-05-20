package com.fiona.trafficnews.di.component

import com.fiona.trafficnews.viewmodel.NewsViewModel
import com.fiona.trafficnews.viewmodel.UserViewModel
import dagger.Component
import com.fiona.trafficnews.di.module.ApiRepositoryModule

import javax.inject.Singleton

@Singleton
@Component(modules = [ApiRepositoryModule::class])
interface ApiRepositoryComponent {
    fun inject(userViewModel: UserViewModel)
    fun inject(newsViewModel: NewsViewModel)
}
