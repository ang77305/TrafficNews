package example.fiona.pixabay.di.component

import com.fiona.trafficnews.ApiRepository
import dagger.Component
import example.fiona.pixabay.di.module.ApiHelperModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiHelperModule::class])
interface ApiHelperComponent {
    fun inject(apiRepository: ApiRepository)
}
