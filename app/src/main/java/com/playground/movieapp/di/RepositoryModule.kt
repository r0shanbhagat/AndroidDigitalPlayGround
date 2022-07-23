package com.playground.movieapp.di

import com.playground.movieapp.contract.Repository
import com.playground.movieapp.data.MoviesRepository
import com.playground.movieapp.data.api.MovieService
import com.playground.movieapp.data.mapper.MovieMapper
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
    ): Repository =
        MoviesRepository(apiService, movieMapper, ioDispatcher)
}
