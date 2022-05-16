package com.digital.playground.di

import com.digital.playground.data.MovieRepository
import com.digital.playground.data.api.MovieService
import com.digital.playground.data.mapper.MovieMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @Details RepositoryModule
 * @Author Roshan Bhagat
 * @constructor Create Repository module
 */
@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {

    /**
     * Provide movie repository
     *
     * @param apiService
     * @param ioDispatcher
     * @return
     */
    @Provides
    fun provideMovieRepository(
        apiService: MovieService,
        movieMapper: MovieMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): com.digital.playground.contract.Repository =
        MovieRepository(apiService, movieMapper, ioDispatcher)

}
