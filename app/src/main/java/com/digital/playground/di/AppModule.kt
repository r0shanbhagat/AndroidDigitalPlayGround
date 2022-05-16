package com.digital.playground.di

import com.digital.playground.data.model.SearchModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Defines all the classes that need to be provided in the scope of the app.
 *@see "https://developer.android.com/training/dependency-injection/hilt-android"
 *
 * Define here all objects that are shared throughout the app, like SharedPreferences, navigators or
 * others. If some of those objects are singletons, they should be annotated with `@Singleton`.
 * @Author Roshan Bhagat
 **/
@InstallIn(ViewModelComponent::class)
@Module
class AppModule {

    @Provides
    fun provideSearchModel(): SearchModel {
        return SearchModel()
    }
}