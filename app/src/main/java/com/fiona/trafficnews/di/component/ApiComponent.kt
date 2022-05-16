package example.fiona.pixabay.di.component

import com.fiona.trafficnews.api.ApiHelper
import dagger.Component

import example.fiona.pixabay.di.module.ApiModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(apiHelper: ApiHelper)
}
