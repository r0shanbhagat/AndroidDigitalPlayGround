package com.playground.movieapp.di

import com.playground.movieapp.BuildConfig
import com.playground.movieapp.data.api.MovieService
import com.playground.movieapp.data.api.MovieServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
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
            parameter("api_key", BuildConfig.API_KEY)
            host = BuildConfig.BASE_URL
            url {
                protocol = URLProtocol.HTTPS
            }
        }

        if (BuildConfig.DEBUG) {
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
        }

        val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    @Provides
    @Singleton
    fun provideApi(client: HttpClient): MovieService = MovieServiceImpl(client)

}