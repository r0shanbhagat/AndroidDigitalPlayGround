package com.playground.movieapp.di

import com.playground.movieapp.ui.adapter.Adapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 * Defines all the classes that need to be provided in the scope of the app.
 *@see "https://developer.android.com/training/dependency-injection/hilt-android"
 *
 * Define here all objects that are used in Fragment scopes,
 * @Author Roshan Bhagat
 **/
@InstallIn(FragmentComponent::class)
@Module
class FragmentModule {

    /**
     * Get movie adapter.
     *
     * @return [Adapter]
     */
    @Provides
    fun getMovieAdapter(): Adapter = Adapter()


}