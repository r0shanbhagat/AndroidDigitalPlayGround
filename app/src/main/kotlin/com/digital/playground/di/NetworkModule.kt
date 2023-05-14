package com.digital.playground.di

import android.util.Log
import com.digital.playground.AppConstant
import com.digital.playground.data.api.MovieService
import com.digital.playground.data.api.MovieServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * @Details NetworkModule
 * @Author Roshan Bhagat
 * @constructor Create Network module
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        //Header
        install(DefaultRequest) {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            url(AppConstant.BASE_URL)
            url {
                parameters.append("api_key", AppConstant.API_KEY)
            }
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = false
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Network", message)
                }

            }
            level = LogLevel.ALL
        }
    }

    @Provides
    @Singleton
    fun provideApi(client: HttpClient): MovieService = MovieServiceImpl(client)

}