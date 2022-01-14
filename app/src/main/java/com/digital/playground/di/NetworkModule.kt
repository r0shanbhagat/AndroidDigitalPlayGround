package com.digital.playground.di

import android.content.Context
import com.digital.playground.BuildConfig
import com.digital.playground.network.ApiConstants
import com.digital.playground.network.ApiService
import com.digital.playground.network.logging.Level
import com.digital.playground.network.logging.Logger
import com.digital.playground.network.logging.LoggingInterceptor
import com.digital.playground.network.logging.NoInternetException
import com.digital.playground.util.AppUtils.isNetworkConnected
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
/**
 * @Details NetworkModule
 * @Author Roshan Bhagat
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLogInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .logger(Logger.DEFAULT)
            .setLevel(Level.BODY)
            .build()
    }

    @Provides
    @Singleton
    fun provideErrorInterceptor(@ApplicationContext context: Context): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            if (!isNetworkConnected(context)) {
                throw NoInternetException(
                    "",
                    Throwable(NoInternetException::class.java.toString())
                )
            }
            val request = chain.request()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttp(error: Interceptor, logging: LoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(error)
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}