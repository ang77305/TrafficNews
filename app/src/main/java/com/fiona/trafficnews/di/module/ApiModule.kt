package example.fiona.pixabay.di.module

import android.util.Log
import com.fiona.trafficnews.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object ApiModule {
    private const val BASE_URL = "https://watch-master-staging.herokuapp.com/api/"

    @Singleton
    @Provides
    fun provideRetrofit(): ApiService {
        var logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i("interceptor msg", message)
            }
        })
        logging.level = HttpLoggingInterceptor.Level.BODY
        var okhttpClient = OkHttpClient().newBuilder().addInterceptor(logging).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)

    }
}


