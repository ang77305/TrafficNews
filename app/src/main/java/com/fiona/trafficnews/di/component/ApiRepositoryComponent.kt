package example.fiona.pixabay.di.component

import com.fiona.trafficnews.NewsViewModel
import com.fiona.trafficnews.UserViewModel
import dagger.Component
import example.fiona.pixabay.di.module.ApiRepositoryModule

import javax.inject.Singleton

@Singleton
@Component(modules = [ApiRepositoryModule::class])
interface ApiRepositoryComponent {
    fun inject(userViewModel: UserViewModel)
    fun inject(newsViewModel: NewsViewModel)
}
