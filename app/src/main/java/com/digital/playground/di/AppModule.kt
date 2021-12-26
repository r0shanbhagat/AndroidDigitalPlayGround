package com.digital.playground.di

import com.digital.playground.repository.model.LoginModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLoginModel(): LoginModel {
        return LoginModel()
    }
}