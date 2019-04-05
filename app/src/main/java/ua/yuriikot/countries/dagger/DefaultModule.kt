package ua.yuriikot.countries.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ua.yuriikot.countries.BuildConfig
import ua.yuriikot.countries.remote.api.CountryApi
import ua.yuriikot.countries.repository.CountryRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DefaultModule(private val application: Application) {

    @Provides
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInretceptor(): Interceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideCountryApi(
        client: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): CountryApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.COUNTRIES_API_URL)
            .client(client)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(converterFactory)
            .build().create(CountryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryRepository(countryApi: CountryApi): CountryRepository {
        return CountryRepository(countryApi)
    }


}